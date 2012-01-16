package openmessenger

import grails.test.*
import grails.validation.ValidationException

class CallbackServiceTests extends GrailsUnitTestCase {
	def callbackService
    protected void setUp() {
        super.setUp()
		callbackService = new CallbackService()
		MockUtils.mockLogging(CallbackService, true)
		
		def callbackMsg = new CallbackMessage(queuename:'test callbackQueue', errorcode:'error test 112', senderId:'openError', 
				msisdn:'openmesenger', content:'test callback msg', sendDate:new Date())
		mockDomain(CallbackMessage)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testHandleMessage() {
		def map = [queuename:'test callbackQueue', errorcode:'error test 112', senderId:'openError', 
				msisdn:'openmesenger', content:'test callback msg', sendDate:new Date()]
		callbackService.handleMessage(map)
		def callbackMsg = CallbackMessage.findBySendDateAndMsisdn(map.sendDate, map.msisdn)
		assertNotNull(callbackMsg)
		assertEquals(map.msisdn, callbackMsg.msisdn)
    }
	
	void testFailHandleMessage() {
		def map = [:]
		callbackService.handleMessage(map)
		def callbackMsg = CallbackMessage.findAll()
		assertEquals(0, CallbackMessage.count())
		
		map = [queuename:'test callbackQueue', errorcode:'error test 112', senderId:'openError', 
				msisdn:'openmesenger', contentx:'test callback msg', sendDate:'asdfasdfasdf']
		callbackService.handleMessage(map)
		callbackMsg = CallbackMessage.findAll()
		assertEquals(0, CallbackMessage.count())
	}
	
	void testCreateCallbackMessage() {
		def map = [errorcode:'error test 112', senderId:'openError',
			msisdn:'openmesenger', content:'test callback msg']		
		shouldFail(ValidationException) {
			callbackService.createCallbackMessage(map)			
		}
	}
}
