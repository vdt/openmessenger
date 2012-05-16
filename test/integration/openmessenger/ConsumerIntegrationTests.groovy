package openmessenger

import grails.test.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class ConsumerIntegrationTests extends GroovyTestCase {
	def consumerService
	
    protected void setUp() {
        super.setUp()
		MockUtils.mockLogging(ConsumerService, true)
		MockUtils.mockLogging(CallbackService, true)
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	void testHandleMessage(){
		def map = [msisdn:'66897753337', content:'Call me RabbitMQ Dude ', isSenderId:true, senderId:'testSenderId', isUnicode:true]
		consumerService.handleMessage(map)
		assertNotNull consumerService.sessionId
	}
	
	void testHandleMessageWithDefaultSenderId(){
		def map = [msisdn:'66897753337', content:'Call me RabbitMQ Dude ', isSenderId:true, isUnicode:true]
		consumerService.handleMessage(map)
		assertNotNull consumerService.sessionId
	}
	
	void testHandleMessageWithOutSenderIdCheckSenderId(){
		def map = [msisdn:'66897753337', content:'Call me RabbitMQ Dude ', isSenderId:false, senderId:"WithoutSender", isUnicode:true]
		consumerService.handleMessage(map)
		assertNotNull consumerService.sessionId
	}
	
	void testHandleMessageWithOutSenderId(){
		def map = [msisdn:'66897753337', content:'Call me RabbitMQ Dude ', isSenderId:false, isUnicode:false]
		consumerService.handleMessage(map)
		assertNotNull consumerService.sessionId
	}
	
    void testSendMessage() {
		//text -> limited 189 characters
		def map = [msisdn:'66890242989', content:'Call me RabbitMQ', isUnicode:true]
		//def map = [msisdn:'66800892412', content:'ทดสอบไทย']
		
		def result = consumerService.sendMessage(map)
		assertTrue result.contains('ID:')
		
		//assertEquals 'http://api.clickatell.com', CH.config.sms.gateway.uri
		//consumerService.handleMessage([msisdn:'66860546930', content:'Hello from OpenMessenger!'])		
		//consumerService.handleMessage([msisdn:'66897777367', content:'Hello from OpenMessenger!'])		
    }
	
	void testHandleMessageFail(){
		CH.config.sms.gateway.user="opendreamx"
		consumerService.sessionId = null
		def map = [msisdn:'66809737799', content:'Call me RabbitMQ Dude ทดสอบไทย ព្រះរាជាណាចក្រកម្ពុជា  tiếng Việt, Việt ngữ', isSenderId:true, date:new Date(), 
			eventId:1, callbackQueue:CH.config.openmessenger.eventCallback, isUnicode:false]
		consumerService.handleMessage(map)
		assertNull consumerService.sessionId
		Thread.sleep(5000)
		def callback = CallbackMessage.findByMsisdn('66809737799')
		assertNotNull callback
		assertEquals(1, CallbackMessage.count())
		assertEquals('66809737799', callback.msisdn)
	}
		
	void testSendMessageFail(){
		CH.config.sms.gateway.user="opendreamx"		
		shouldFail(ConsumerServiceException) {
			consumerService.sessionId = "error"
			def map = [msisdn:'66809737799', content:'Call me RabbitMQ Dude ทดสอบไทย ព្រះរាជាណាចក្រកម្ពុជា  tiếng Việt, Việt ngữ', isSenderId:true, date:new Date(), 
				eventId:1, callbackQueue:CH.config.openmessenger.eventCallback, isUnicode:false]
			consumerService.sendMessage(map)
		}		
	}
		
	void testGetNewSessionFail(){
		CH.config.sms.gateway.user="opendreamx"
		
		shouldFail(ConsumerServiceException) {
			def map = [msisdn:'66809737799', content:'Call me RabbitMQ Dude ทดสอบไทย ព្រះរាជាណាចក្រកម្ពុជា  tiếng Việt, Việt ngữ', isSenderId:true, date:new Date(), 
				eventId:1, callbackQueue:CH.config.openmessenger.eventCallback, isUnicode:false]
			consumerService.getNewSession(map)
		}
	}
	
	void testGetNewSessionPass(){
		CH.config.sms.gateway.user="opendream"
		def map = [msisdn:'66809737799', content:'Call me RabbitMQ Dude ทดสอบไทย ព្រះរាជាណាចក្រកម្ពុជា  tiếng Việt, Việt ngữ', isSenderId:true, date:new Date(), 
				eventId:1, callbackQueue:CH.config.openmessenger.eventCallback, isUnicode:false]
		consumerService.getNewSession(map)
		assertNotNull consumerService.sessionId
		assertNotNull consumerService.lastPing
	}
	
	void testPing(){
		def result = consumerService.ping()
		assertTrue result.contains("OK")
	}
}
