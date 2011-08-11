package openmessenger

import com.rabbitmq.client.*

import groovyx.net.http.*
import groovyx.net.http.ContentType.*
import groovyx.net.http.Method.*
import org.apache.commons.lang.CharUtils
import org.apache.commons.lang.StringUtils;


import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class ConsumerService {

    static transactional = true
	static rabbitQueue = 'openmessenger'
	String sessionId
	Long lastPing 

    void handleMessage(String msg) {
		println "Received String Message: ${msg}"
    }
	
	void handleMessage(Map map) {				 
		try{	
			sendMessage(map)	
		}catch (Exception e) {
			log.error(e.toString(),e)
		}		
	}
	
	def sendMessage(Map map){
		if(!sessionId) {
		 	getNewSession(map)			
		}else {
			if(isExpire()){ 
				def result = ping()
				if(result.contain('ERR')) getNewSession(map)
			}			
		}
		
		def result = withHttp(uri:CH.config.sms.gateway.uri) {
			def html = get(path :CH.config.sms.gateway.path,
								query : [session_id:sessionId, to:map.msisdn,
										text:convertToUnicode(map.content), from:CH.config.sms.gateway.senderId, 
										unicode:1, concat:getConcatinationSize(map.content)]) 
			
			}
		
		if(!result.toString().contains('ID:'))
			throw new ConsumerServiceException(errorMsg:result.toString(), senderId:CH.config.sms.gateway.senderId, msisdn:map.msisdn)	
			
		return result.toString()	
	}
	
	def getNewSession(Map map){
		def result = withHttp(uri:CH.config.sms.gateway.suri) {
				get(path : CH.config.sms.gateway.auth, query : [api_id:CH.config.sms.gateway.apiId,
																user:CH.config.sms.gateway.user,
																password:CH.config.sms.gateway.password])			
		}
		
		if(result.toString().contains('OK')){
			sessionId = getNewSessionId(result.toString()) // OK, ERR
			def date = new Date()
			lastPing = date.time
		}else if(result.toString().contains('ERR')){
			throw new ConsumerServiceException(errorMsg:result.toString(), senderId:CH.config.sms?.gateway?.senderId, msisdn:map?.msisdn)
			// getNewSession()
		}
	}
	
	Boolean isExpire(){
		def now = System.currentTimeMillis()
		def inactive = now - lastPing
		return inactive > CH.config.sms.gateway.inactivity
	}	
	
	String ping(){
		def result = withHttp(uri:CH.config.sms.gateway.uri) {
			get(path : CH.config.sms.gateway.ping, query : [session_id:sessionId]) // OK, ERR
		}
		return result.toString()
	}
	
	String getNewSessionId(String sessionString){
		def start = sessionString.indexOf(':')
		sessionString.substring(start+1, sessionString.length()).trim()
	}
	
	def convertToUnicode(String msg){
		StringBuffer str = new StringBuffer()
		msg.toCharArray().each {
			str.append( CharUtils.unicodeEscaped(it) )
		}
		StringUtils.remove(str.toString(), "\\u")
	}
	
	def getConcatinationSize(String msg){
		msg.size().div(70).plus(msg.size()%70?1:0).intValue()			
	}
}
