package openmessenger

import grails.test.*
import java.text.SimpleDateFormat

class EventTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidate() {
        def newEvent = new Event()
        mockForConstraintsTests(Event, [newEvent])
        assertFalse newEvent.validate()

         newEvent = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
            status: 'DAMN')
        assertFalse newEvent.validate()
        assertEquals 'inList', newEvent.errors['status']
				
         newEvent = new Event(name: 'The Championships, Wimbledon', 
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
            status: 'NORMAL')	
	
        assertTrue newEvent.validate()	
    }
	
    void testMockGorm(){
        def newEvent = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
            status: 'NORMAL')
        mockDomain(Event, [newEvent])
		
        assertNotNull newEvent.save()
		
        def firstSubscriber = new Subscriber(msisdn: '1234567890A')
        def secondSubscriber = new Subscriber(msisdn: '1234567890B')
        def thirdSubscriber = new Subscriber(msisdn: '1234567890C')
		
        newEvent.addToSubscribers(firstSubscriber)
        newEvent.addToSubscribers(secondSubscriber)
        newEvent.addToSubscribers(thirdSubscriber)
		
        def targetEvent = Event.get(newEvent.id)
        def subscribers = targetEvent.subscribers.collect { it.msisdn }
        assertEquals(['1234567890A', '1234567890B', '1234567890C'], subscribers.sort())
    }     

	void testAddMessageToEvent(){
         def newEvent = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
            status: 'NORMAL')

   	   	def firstMessage  = new Message (title:"the final", content:"Nadal wes defeated by Novak, 1-3")  


		mockDomain(Event, [newEvent])
		newEvent.save()
		
		newEvent.addToMessages(firstMessage)
		def targetEvent = Event.get(newEvent.id)
		assertEquals 1, targetEvent.messages.size()
		
	}
}
