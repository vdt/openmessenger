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
	def sessionId

    def handleMessage(String msg) {
		println "Received String Message: ${msg}"
    }
	
	def handleMessage(Map map){
		try{
			/*if(!sessionId) {
			def clickatell = withHttp(uri: "http://api.clickatell.com") {
				def html = get(path : '/http/auth', query : [api_id:'3312346',user:'opendream',password:'Tdb5vzt6zuMAhG'])
			}
			println "class ${clickatell.getClass()}"
			println clickatell
			println clickatell.name
			println clickatell.namespacePrefix
			println clickatell.size()
			clickatell.childNodes().each { println "${it.name()} ${it.text()}" }
			println clickatell.toString()
			sessionId = clickatell.getAt('OK')
			println sessionId
			}*/
			
			def result = withHttp(uri:CH.config.sms.gateway.uri) {
				def html = get(path :CH.config.sms.gateway.path, 
								query : [api_id:CH.config.sms.gateway.apiId, user:CH.config.sms.gateway.user, 
										password:CH.config.sms.gateway.password, to:map.msisdn, 
										text:convertToUnicode(map.content), unicode:1, concat:getConcatinationSize(map.content)])
			}					
			result			
		}catch(Exception e){
			println e
		}		
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
