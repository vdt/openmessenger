package openmessenger

import grails.test.*

class SubscriberTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidattion() {
        def existingSubscriber = new Subscriber(msisdn:'66809737799')
        mockForConstraintsTests(Subscriber,[existingSubscriber])
		
        def newSubscriber = new Subscriber()
        assertFalse newSubscriber.validate()
        assertEquals 'nullable', newSubscriber.errors["msisdn"]
        assertEquals 'nullable', newSubscriber.errors['active']
		
        newSubscriber =  new Subscriber(msisdn:'6680973')
        assertFalse newSubscriber.validate()
        assertEquals 'size', newSubscriber.errors["msisdn"]
        assertEquals 'nullable', newSubscriber.errors['active']
		
        newSubscriber =  new Subscriber(msisdn:'66809737799')
        assertFalse newSubscriber.validate()
        assertEquals 'unique', newSubscriber.errors["msisdn"]
        assertEquals 'nullable', newSubscriber.errors['active']
		
        newSubscriber =  new Subscriber(msisdn:'66809737799', active: 'Q')
        assertFalse newSubscriber.validate()
        assertEquals 'unique', newSubscriber.errors["msisdn"]
        assertEquals 'inList', newSubscriber.errors['active']
		
        newSubscriber = new Subscriber(msisdn: '66809737798', active: 'Y')
        assertTrue newSubscriber.validate()
    }
}
