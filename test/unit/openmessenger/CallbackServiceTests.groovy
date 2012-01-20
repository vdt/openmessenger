package openmessenger

import grails.test.*
import grails.validation.ValidationException
import openmessenger.Event.Status
import openmessenger.Event.Type
import java.text.SimpleDateFormat

class CallbackServiceTests extends GrailsUnitTestCase {
	def callbackService
    protected void setUp() {
        super.setUp()
		callbackService = new CallbackService()
		MockUtils.mockLogging(CallbackService, true)
		
		def callbackMsg = new CallbackMessage(queuename:'test callbackQueue', errorcode:'error test 112', senderId:'openError', 
				msisdn:'openmesenger', content:'test callback msg', sendDate:new Date())
		mockDomain(CallbackMessage)
		
		def eventInstances = [new Event(name: 'The Championships, Wimbledon',
			description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
			occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
			status:Status.NORMAL, type:Type.GROUP_CHAT)]
		mockDomain(Event, eventInstances)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testHandleMessage() {
		assertEquals(1, Event.count())
		def map = [eventId:1, queuename:'test callbackQueue', errorcode:'error test 112', senderId:'openError', 
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
		
		map = [event:Event.get(1), queuename:'test callbackQueue', errorcode:'error test 112', senderId:'openError', 
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
