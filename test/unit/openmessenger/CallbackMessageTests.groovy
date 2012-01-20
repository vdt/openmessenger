package openmessenger

import grails.test.*
import openmessenger.Event.Status
import openmessenger.Event.Type
import java.text.SimpleDateFormat

class CallbackMessageTests extends GrailsUnitTestCase {
	
    protected void setUp() {
        super.setUp()
		def eventInstances = [new Event(name: 'The Championships, Wimbledon',
			description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
			occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
			status:Status.NORMAL, type:Type.GROUP_CHAT)]
		mockDomain(Event, eventInstances)		
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidate() {
		assertEquals(1, Event.count())
		def callbackMsg = new CallbackMessage(event:Event.get(1), queuename:'test callbackQueue', errorcode:'error test 112', senderId:'openError',
			msisdn:'openmesenger', content:'test callback msg', sendDate:new Date())
		mockForConstraintsTests(CallbackMessage, [callbackMsg])
		assertTrue(callbackMsg.validate())
    }
	
	void testFailValidate() {
		def callbackMsg = new CallbackMessage(errorcode:'error test 112', senderId:'openError',
			msisdn:'openmesenger', content:'test callback msg')
		mockForConstraintsTests(CallbackMessage, [callbackMsg])
		assertFalse(callbackMsg.validate())
	}
	
	void testSave(){
		def callbackMsg = new CallbackMessage(event:Event.get(1), queuename:'test callbackQueue', errorcode:'error test 112', senderId:'openError',
			msisdn:'openmesenger', content:'test callback msg', sendDate:new Date())
		mockDomain(CallbackMessage, [callbackMsg])
		callbackMsg.save()
		assertEquals(1, CallbackMessage.count())
		assertEquals(1, callbackMsg.id)
	}
}
