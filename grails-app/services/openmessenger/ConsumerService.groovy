package openmessenger

import com.rabbitmq.client.*

import groovyx.net.http.*
import groovyx.net.http.ContentType.*
import groovyx.net.http.Method.*
import org.apache.commons.lang.CharUtils
import org.apache.commons.lang.StringUtils;

class ConsumerService {

    static transactional = true
	static rabbitQueue = 'openmessenger'	

    def handleMessage(String msg) {
		println "Received String Message: ${msg}"
    }
	
	def handleMessage(Map map){
		try{			
			def result = withHttp(uri: "http://api.clickatell.com") {
				def html = get(path : '/http/sendmsg', query : [api_id:'3312346',user:'opendream',password:'Tdb5vzt6zuMAhG', to:map.to, text:convertToUnicode(map.text), unicode:1])
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
}
