package openmessenger

import grails.test.*

class CallbackMessageTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidate() {
		def callbackMsg = new CallbackMessage(queuename:'test callbackQueue', errorcode:'error test 112', senderId:'openError',
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
		def callbackMsg = new CallbackMessage(queuename:'test callbackQueue', errorcode:'error test 112', senderId:'openError',
			msisdn:'openmesenger', content:'test callback msg', sendDate:new Date())
		mockDomain(CallbackMessage, [callbackMsg])
		callbackMsg.save()
		assertEquals(1, CallbackMessage.count())
		assertEquals(1, callbackMsg.id)
	}
}
