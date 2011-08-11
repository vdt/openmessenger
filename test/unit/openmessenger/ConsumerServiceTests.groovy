package openmessenger

import grails.test.*
import org.apache.commons.lang.CharUtils
import org.apache.commons.lang.StringUtils;

class ConsumerServiceTests extends GrailsUnitTestCase {
	def consumerService
	
    protected void setUp() {
		consumerService = new ConsumerService()
		/*mockConfig ('''
		sms.gateway.uri="path"		
		''')*/
		mockConfig ('''
		sms.gateway.senderId="opendream"
		sms.gateway.inactivity=5000
		''')
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testHandleMessage() {
		def maps = [[uri:'http1', msisdn:'66890242989', content:'Call me RabbitMQ dude'], 
			[uri:'http2', msisdn:'66890242989', content:'Call me RabbitMQ dude'], 
			[uri:'http3', msisdn:'66890242989', content:'Call me RabbitMQ dude']]
		def counter = 0
		consumerService.metaClass.withHttp = {Map map, Closure closure -> 
			counter++
			'ID:'	
		}

		maps.each { 
			consumerService.handleMessage(it)			
		}
		
		assertEquals 3, counter
    }
	
	void testConsumerServiceException() {
		def arg = [uri:'http1', msisdn:'66890242989', content:'Call me RabbitMQ dude']
		consumerService.metaClass.withHttp = {Map map, Closure closure -> 'ERR: 123 Invalid Sender ID'}
		shouldFail(ConsumerServiceException) {  
			consumerService.sendMessage(arg)
		}
	}
	
	void testConvertToUnicode() {		
		String msg = 'ไทย'
		String result = consumerService.convertToUnicode(msg)
		assertEquals '0e440e170e22', result
	}
	
	void testGetConcatinationSize(){		
		assertEquals 1 , consumerService.getConcatinationSize('asdf')		
		assertEquals 2 , consumerService.getConcatinationSize('Call me RabbitMQ Dude ทดสอบไทย ព្រះរាជាណាចក្រកម្ពុជា  tiếng Việt, Việt ngữ')	
	}
	
	void testGetNewSessionFail(){
		def arg = [uri:'http1', msisdn:'66890242989', content:'Call me RabbitMQ dude']
		consumerService.metaClass.withHttp = {Map map, Closure closure -> 'ERR: 123 Invalid, asdfasdf'}
		shouldFail(ConsumerServiceException) {
			consumerService.getNewSession(arg)
		}			
	}	
	
	void testGetNewSessionPass(){
		def arg = [uri:'http1', msisdn:'66890242989', content:'Call me RabbitMQ dude']
		consumerService.metaClass.withHttp = {Map map, Closure closure -> 'OK : 12345'}
		consumerService.getNewSession(arg)
		assertEquals '12345', consumerService.sessionId
	}
	
	void testIsExpire(){
		consumerService.lastPing = System.currentTimeMillis()
		assertFalse(consumerService.isExpire())
		
		Thread.sleep(5500)
		assertTrue(consumerService.isExpire())
	}
	
	void testPing(){
		def counter = 0
		consumerService.metaClass.withHttp = {Map map, Closure closure -> 
											counter++
											'OK : 12345'}
		consumerService.ping()
		assertEquals 1, counter
	}
	
	void testGetNewSessionId(){		
		def id = consumerService.getNewSessionId('OK : 12345 ')
		assertEquals '12345', id
	}
}
