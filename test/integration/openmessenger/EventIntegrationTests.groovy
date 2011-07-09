package openmessenger

import grails.test.*

class EventIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSave() {
        def event = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new Date(), //new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
            status: 'NORMAL')
		
        event.validate()
		
        if(event.hasErrors()){
            event.errors.each {
                println it
            }
        }
		
        assertNotNull event.save()
        assertNotNull event.id
    }
	
    void testEvilSave(){
        def event = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new Date(), //new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
            status: 'NOT IN LIST DUDE')
		
        assertNull event.save()
    }
	
    void testAddToSubscribers(){
        def event = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new Date(), //new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
            status: 'NORMAL')
		
        event.validate()
		
        if(event.hasErrors()){
            event.errors.each {
                println it
            }
        }
        assertNotNull event.save()
		
        def firstSubscriber = new Subscriber(msisdn: '1234567890A')
        def secondSubscriber = new Subscriber(msisdn: '1234567890B')
        def thirdSubscriber = new Subscriber(msisdn: '1234567890C')
		
        event.addToSubscribers(firstSubscriber)
        event.addToSubscribers(secondSubscriber)
        event.addToSubscribers(thirdSubscriber)
		
        assertEquals 3, Event.get(event.id).subscribers.size()
    }
	
    void testGetAllSubscribers(){
        def event = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new Date(),
            status: 'NORMAL')
		
        event.validate()
		
        if(event.hasErrors()){
            event.errors.each {
                println it
            }
        }
        assertNotNull event.save()
		
        def firstSubscriber = new Subscriber(msisdn: '1234567890A')
        def secondSubscriber = new Subscriber(msisdn: '1234567890B')
        def thirdSubscriber = new Subscriber(msisdn: '1234567890C')
		
        event.addToSubscribers(firstSubscriber)
        event.addToSubscribers(secondSubscriber)
        event.addToSubscribers(thirdSubscriber)
		
        def targetEvent = Event.get(event.id)
        def subscribers = targetEvent.subscribers.collect { it.msisdn }
        assertEquals(['1234567890A', '1234567890B', '1234567890C'], subscribers.sort())
    }
}
