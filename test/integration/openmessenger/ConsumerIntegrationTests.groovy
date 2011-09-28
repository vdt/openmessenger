package openmessenger

import grails.test.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class ConsumerIntegrationTests extends GroovyTestCase {
	def consumerService
	
    protected void setUp() {
        super.setUp()
		MockUtils.mockLogging(ConsumerService, true)
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	void testHandleMessage(){
		def map = [msisdn:'66897753337', content:'Call me RabbitMQ Dude ', isSenderId:true, senderId:'testSenderId']
		consumerService.handleMessage(map)
		assertNotNull consumerService.sessionId
	}
	
	void testHandleMessageWithDefaultSenderId(){
		def map = [msisdn:'66897753337', content:'Call me RabbitMQ Dude ', isSenderId:true]
		consumerService.handleMessage(map)
		assertNotNull consumerService.sessionId
	}
	
	void testHandleMessageWithOutSenderIdCheckSenderId(){
		def map = [msisdn:'66897753337', content:'Call me RabbitMQ Dude ', isSenderId:false, senderId:"WithoutSender"]
		consumerService.handleMessage(map)
		assertNotNull consumerService.sessionId
	}
	
	void testHandleMessageWithOutSenderId(){
		def map = [msisdn:'66897753337', content:'Call me RabbitMQ Dude ', isSenderId:false]
		consumerService.handleMessage(map)
		assertNotNull consumerService.sessionId
	}
	
    void testSendMessage() {
		//text -> limited 189 characters
		def map = [msisdn:'66890242989', content:'Call me RabbitMQ']
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
		def map = [msisdn:'66809737799', content:'Call me RabbitMQ Dude ทดสอบไทย ព្រះរាជាណាចក្រកម្ពុជា  tiếng Việt, Việt ngữ', isSenderId:true]
		consumerService.handleMessage(map)
		assertNull consumerService.sessionId		
	}
		
	void testSendMessageFail(){
		CH.config.sms.gateway.user="opendreamx"		
		shouldFail(ConsumerServiceException) {
			consumerService.sessionId = null
			def map = [msisdn:'66809737799', content:'Call me RabbitMQ Dude ทดสอบไทย ព្រះរាជាណាចក្រកម្ពុជា  tiếng Việt, Việt ngữ']
			consumerService.sendMessage(map)
		}		
	}
		
	void testGetNewSessionFail(){
		CH.config.sms.gateway.user="opendreamx"
		
		shouldFail(ConsumerServiceException) {
			def map = [msisdn:'66809737799', content:'Call me RabbitMQ Dude ทดสอบไทย ព្រះរាជាណាចក្រកម្ពុជា  tiếng Việt, Việt ngữ']
			consumerService.getNewSession(map)
		}
	}
	
	void testGetNewSessionPass(){
		CH.config.sms.gateway.user="opendream"
		def map = [msisdn:'66809737799', content:'Call me RabbitMQ Dude ทดสอบไทย ព្រះរាជាណាចក្រកម្ពុជា  tiếng Việt, Việt ngữ']
		consumerService.getNewSession(map)
		assertNotNull consumerService.sessionId
		assertNotNull consumerService.lastPing
	}
	
	void testPing(){
		def result = consumerService.ping()
		assertTrue result.contains("OK")
	}
}
