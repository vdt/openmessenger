package openmessenger

import grails.test.*

class SubscriberControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testListAllSubscribers() {
        def firstSubscriber = new Subscriber(msisdn: '66809737798', active: 'Y')
        def secondSubscriber = new Subscriber(msisdn: '66809737799', active: 'Y')
        mockDomain(Subscriber, [firstSubscriber, secondSubscriber])
        
        controller.listAllSubscribers()
        assertEquals "listAllSubscribers", controller.renderArgs.template
    }    

}
