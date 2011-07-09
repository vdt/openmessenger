package openmessenger

import grails.test.*

class SubscriberIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testFirstSave() {
        def newSubscriber = new Subscriber(msisdn: '66809737799', active: 'Y')
        assertNotNull newSubscriber.save()
        assertNotNull newSubscriber.id
        def targetSubscriber = Subscriber.get(newSubscriber.id)
        assertEquals targetSubscriber.msisdn, newSubscriber.msisdn
    }
	
    void testSaveAndUpdate(){
        def newSubscriber = new Subscriber(msisdn:'66809737799', active: 'Y')
        assertNotNull newSubscriber.save()
		
        def targetSubscriber = Subscriber.get(newSubscriber.id)
        targetSubscriber.msisdn = '66809737798'
        targetSubscriber.active = 'N'
        targetSubscriber.save()
		
        def editedSubscriber = Subscriber.get(newSubscriber.id)
        assertEquals '66809737798', editedSubscriber.msisdn
        assertEquals 'N', editedSubscriber.active
    }
	
    void testSaveAndDelete(){
        def newSubscriber = new Subscriber(msisdn:'66809737799', active: 'Y')
        assertNotNull newSubscriber.save()
		
        def targetSubscriber = Subscriber.get(newSubscriber.id)
        targetSubscriber.delete()
		
        assertFalse Subscriber.exists(newSubscriber.id)
    }
	
    void testEvilSaveErrors(){
        def newSubscriber = new Subscriber(msisdn:'668097', active: 'Q')
        assertFalse newSubscriber.validate()
        assertTrue newSubscriber.hasErrors()
		
        def errors = newSubscriber.errors
        assertEquals 'size.toosmall', errors.getFieldError('msisdn').code
        assertEquals 'not.inList', errors.getFieldError('active').code
		
        newSubscriber = new Subscriber(msisdn:'12345123451234512345', active: 'X')
        assertFalse newSubscriber.validate()
        assertTrue newSubscriber.hasErrors()
		
        errors = newSubscriber.errors
        assertEquals 'size.toobig', errors.getFieldError('msisdn').code
        assertEquals 'not.inList', errors.getFieldError('active').code
    }
	
    void testEvilSaveCorrect(){
        def newSubscriber = new Subscriber(msisdn:'668097', active: 'Q')
        assertFalse newSubscriber.validate()
        assertTrue newSubscriber.hasErrors()
        assertNull newSubscriber.save()
		
        newSubscriber.msisdn = '66809737799'
        newSubscriber.active = 'Y'
		
        assertNotNull newSubscriber.save()
    }
}
