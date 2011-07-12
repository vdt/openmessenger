package openmessenger
import java.text.SimpleDateFormat
import grails.test.*

class EventServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testFindByNameLikeKeyword() {
        def eventInstances = [new Event(name: 'The Championships, Wimbledon',
                description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
                occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
                status: 'NORMAL'), 
            new Event(name: 'The Australian Open',
                description: 'The tournament is held in the middle of the Australian summer, in the last fortnight of the month of January; thus an extreme-heat policy is put into play when temperatures reach dangerous levels.',
                occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
                status: 'NORMAL')]
		
        mockDomain(Event, eventInstances)
        assertEquals 2, Event.list().size()
        def theWimbledon =  Event.findAllByNameLike("%Wim%")
        assertEquals 1, theWimbledon.size()
        assertEquals "The Championships, Wimbledon", theWimbledon.first().name
        def eventService = new EventService()
        assertNotNull eventService.findAllEventByNameLikeKeyword('Wim')
    }
    
    void testFindByStatus(){
        def eventInstances = [new Event(name: 'The Championships, Wimbledon',
                description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
                occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
                status: 'NORMAL'), 
            new Event(name: 'The Australian Open',
                description: 'The tournament is held in the middle of the Australian summer, in the last fortnight of the month of January; thus an extreme-heat policy is put into play when temperatures reach dangerous levels.',
                occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
                status: 'STABLE'),
            new Event(name: 'The Roland Garros',
                description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
                occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
                status: 'NORMAL'), 
            new Event(name: 'The US Open',
                description: 'The tournament is held in the middle of the Australian summer, in the last fortnight of the month of January; thus an extreme-heat policy is put into play when temperatures reach dangerous levels.',
                occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
                status: 'CRITICAL')]
        
        mockDomain(Event, eventInstances)
        assertEquals 4, Event.list().size()
        def theWimbledon =  Event.findAllByStatus("NORMAL")
        assertEquals 2, theWimbledon.size()
        
        def eventService = new EventService()
        assertNotNull eventService.findAllEventByStatus('NORMAL')        
    }
    
    void testSubscriberToEvent(){
        def eventInstance = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
            status: 'NORMAL')
        def newSubscriber = new Subscriber(msisdn: '66809737798', active: 'Y')    
        
        
        mockDomain(Event, [eventInstance])
        mockDomain(Subscriber, [newSubscriber])
        
        newSubscriber.save()
        eventInstance.save()
        
        
        assertNotNull eventInstance.id
        
        
        def eventService = new EventService()
        eventService.subscribeToEvent(1,"66809737798")
        
        def targetEvent  = Event.get(1)
        assertEquals 1, targetEvent.subscribers.size()
    }                      

  void testUnsubscriberFromEvent(){
        def eventInstance = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
            status: 'NORMAL')
        def newSubscriber = new Subscriber(msisdn: '66809737798', active: 'Y')    
        def secondSubscriber = new Subscriber(msisdn: '66809737799', active: 'Y')   

        mockDomain(Event, [eventInstance])
        mockDomain(Subscriber, [newSubscriber, secondSubscriber])

 
        eventInstance.save()


        assertNotNull eventInstance.id
         
		eventInstance.addToSubscribers(newSubscriber)
		eventInstance.addToSubscribers(secondSubscriber)  		

        def eventService = new EventService()
        eventService.unsubscribeFromEvent(eventInstance.id , "66809737798")

        def targetEvent  = Event.get(1)
        assertEquals 1, targetEvent.subscribers.size()
    }

	void testShowEvent(){
        def eventInstance = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
            status: 'NORMAL')
        def newSubscriber = new Subscriber(msisdn: '66809737798', active: 'Y')    
        
        
        mockDomain(Event, [eventInstance])
        mockDomain(Subscriber, [newSubscriber])
        
        newSubscriber.save()
        eventInstance.save()
        
        
        assertNotNull eventInstance.id
        
        
        def eventService = new EventService()
        def targetEvent = eventService.findEventById(1)
		assertEquals "The Championships, Wimbledon", targetEvent.name
	}
}
