package openmessenger
import java.security.Principal;
import java.text.SimpleDateFormat
import grails.test.*
import openmessenger.Event.Status
import openmessenger.Event.Type

class EventServiceTests extends GrailsUnitTestCase {
	def springSecurityService
	
    protected void setUp() {
        super.setUp()
		MockUtils.mockLogging(EventService, true)
		springSecurityService = new HashMap()
		springSecurityService.principal = new HashMap()
		springSecurityService.principal.username = 'default username'
		
		mockConfig ('''
		openmessenger.eventCallback="eventCallback"
		''')
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testFindByNameLikeKeyword() {
        def eventInstances = [new Event(name: 'The Championships, Wimbledon',
                description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
                occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
                status:Status.NORMAL, type:Type.GROUP_CHAT), 
            new Event(name: 'The Australian Open',
                description: 'The tournament is held in the middle of the Australian summer, in the last fortnight of the month of January; thus an extreme-heat policy is put into play when temperatures reach dangerous levels.',
                occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
                status:Status.NORMAL, type:Type.GROUP_CHAT)]
		
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
                status:Status.NORMAL, type:Type.GROUP_CHAT), 
            new Event(name: 'The Australian Open',
                description: 'The tournament is held in the middle of the Australian summer, in the last fortnight of the month of January; thus an extreme-heat policy is put into play when temperatures reach dangerous levels.',
                occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
                status:Status.NORMAL, type:Type.GROUP_CHAT),
            new Event(name: 'The Roland Garros',
                description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
                occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
                status:Status.STABLE, type:Type.GROUP_CHAT), 
            new Event(name: 'The US Open',
                description: 'The tournament is held in the middle of the Australian summer, in the last fortnight of the month of January; thus an extreme-heat policy is put into play when temperatures reach dangerous levels.',
                occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
                status:Status.STABLE, type:Type.GROUP_CHAT)]
        
        mockDomain(Event, eventInstances)
        assertEquals 4, Event.list().size()
        def theWimbledon =  Event.findAllByStatus(Status.NORMAL)
        assertEquals 2, theWimbledon.size()
        
        def eventService = new EventService()
        assertNotNull eventService.findAllEventByStatus(Status.NORMAL)        
    }
    
    void testSubscriberToEvent(){
        def eventInstance = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
            status:Status.NORMAL, type:Type.GROUP_CHAT)
        def newSubscriber = new Subscriber(msisdn: '66809737798', active: 'Y')    
		def defaultGateway = new Gateway(prefix:'00', name:'inter_clickatell', queueName:'openmessenger', createdBy:'admin')
		def dtacGateway = new Gateway(prefix:'66', name:'th_dtac', queueName:'openmessenger_dtac', createdBy:'admin')
        
        mockDomain(Event, [eventInstance])
        mockDomain(Subscriber, [newSubscriber])
		mockDomain(Gateway, [defaultGateway,dtacGateway])
        
        newSubscriber.save()
        eventInstance.save()
		defaultGateway.save()
		dtacGateway.save()
        
        assertNotNull eventInstance.id
        
        
        def eventService = new EventService()
        eventService.subscribeToEvent(1,"66809737798")
        
        def targetEvent  = Event.get(1)
        assertEquals 1, targetEvent.subscribers.size()
		
		def subscriberInstance = Subscriber.findByMsisdn('66809737798')
		assertEquals 'th_dtac', subscriberInstance.gateway.name
    }  
	
	void testSubscriberListToEvent(){
		def eventInstance = new Event(name: 'The Championships, Wimbledon',
			description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
			occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
			status:Status.NORMAL, type:Type.GROUP_CHAT)
		def newSubscriber = new Subscriber(msisdn: '66809737791', active: 'Y')	
		def defaultGateway = new Gateway(prefix:'00', name:'inter_clickatell', queueName:'openmessenger', createdBy:'admin')
		def dtacGateway = new Gateway(prefix:'66', name:'th_dtac', queueName:'openmessenger_dtac', createdBy:'admin')
		
		mockDomain(Event, [eventInstance])
		mockDomain(Subscriber, [newSubscriber])
		mockDomain(Gateway, [defaultGateway,dtacGateway])
		
		newSubscriber.save()
		eventInstance.save()
		defaultGateway.save()
		dtacGateway.save()
				
		assertNotNull eventInstance.id
		
		
		def eventService = new EventService()
		eventService.subscribeToEvent(1,"66809737791")
		
		def targetEvent  = Event.get(1)
		assertEquals 1, targetEvent.subscribers.size()
		
		def subscriberFileService = mockFor(SubscriberFileService)
		subscriberFileService.demand.parseCsv(1..1) { file ->["66809737791", "66809737792", "66809737793", "62809737794", "66809737795"]}		
		
		eventService.subscriberFileService = subscriberFileService.createMock()
		eventService.subscribeToEvent(targetEvent.id, new File(" "))
				
		targetEvent  = Event.get(1)
		assertEquals 5, targetEvent.subscribers.size()
				
		def subscriberInstance = Subscriber.findByMsisdn('66809737791')
		assertEquals 'th_dtac', subscriberInstance.gateway.name
		
		subscriberInstance = targetEvent.subscribers.find{ it.msisdn == '62809737794'}
		println subscriberInstance
		assertEquals 'inter_clickatell', subscriberInstance.gateway.name
	}

    void testListEventSubscribers(){
         def eventInstance = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
            status:Status.NORMAL, type:Type.GROUP_CHAT)  
         def moreEventInstance = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
            status:Status.NORMAL, type:Type.GROUP_CHAT)

        def newSubscriberA = new Subscriber(msisdn: '66809737798', active: 'Y')  
        def newSubscriberB = new Subscriber(msisdn: '66809737799', active: 'Y')  
        def newSubscriberC = new Subscriber(msisdn: '66809737790', active: 'Y')  
 	                      
        mockDomain(Event, [eventInstance, moreEventInstance])     
     
		eventInstance.addToSubscribers(newSubscriberA)
		eventInstance.addToSubscribers(newSubscriberB)
		eventInstance.addToSubscribers(newSubscriberC)

		moreEventInstance.addToSubscribers(newSubscriberA)
		moreEventInstance.addToSubscribers(newSubscriberB)
		moreEventInstance.addToSubscribers(newSubscriberC)   
		
		assertNotNull eventInstance.id
		def event = Event.get(eventInstance.id)
		assertEquals 3, event.subscribers.size()
		
				
	}          

  	void testUnsubscriberFromEvent(){
        def eventInstance = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
            status:Status.NORMAL, type:Type.GROUP_CHAT)
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
            status:Status.NORMAL, type:Type.GROUP_CHAT)
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
	
	void testPostMessageToEventSubscriber(){
        def eventInstance = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
            status:Status.NORMAL, type:Type.GROUP_CHAT)
 		
		def subscribers = [	new Subscriber(msisdn: '66809737791', active: 'Y'),
		 					new Subscriber(msisdn: '66809737792', active: 'Y'),
							new Subscriber(msisdn: '66809737793', active: 'Y'),
							new Subscriber(msisdn: '66809737794', active: 'Y'),
							new Subscriber(msisdn: '66809737795', active: 'Y')	]
		
		mockDomain(Event, [eventInstance])   
		mockDomain(Subscriber, [subscribers])  
		
		eventInstance.save()        
		/*subscribers.each{
			eventInstance.addToSubscribers(it) 
	    } */ 
		
		eventInstance.subscribers = subscribers
	
		def targetEvent = Event.get(eventInstance.id)
		assertEquals 5, targetEvent.subscribers.size()
	}

	void testSendMessage(){
		
		mockDomain(Gateway,[new Gateway(prefix:'00', name:'inter_clickatell', queueName:'openmessenger', createdBy:'admin'),
							new Gateway(prefix:'66', name:'th_dtac', queueName:'openmessenger_dtac', createdBy:'admin')])
		
		def eventInstance = new Event(name: 'The Championships, Wimbledon',
            description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
            occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
            status:Status.NORMAL, type:Type.GROUP_CHAT)
 		
		/*def subscribers = [	new Subscriber(msisdn: '66809737791', active: 'Y'),
		 					new Subscriber(msisdn: '66809737792', active: 'Y'),
							new Subscriber(msisdn: '66809737793', active: 'Y'),
							new Subscriber(msisdn: '66809737794', active: 'Y'),
							new Subscriber(msisdn: '66809737795', active: 'Y')	]*/
		def subscribers = [	'66809737791', '66809737792',
							'66809737793', '66809737794',
							'66809737795']
		
		def initMessage = new Message(title:"title", content:"first content", createdDate:new Date())
		
		mockDomain(Event, [eventInstance])   
		mockDomain(Subscriber, [subscribers])
		mockDomain(MessageLog)

		eventInstance.save()
		
		def eventService = new EventService()
		subscribers.each {
			eventService.subscribeToEvent(eventInstance.id, it)
		}
		//eventInstance.subscribers = subscribers
	    
		eventInstance.addToMessages(initMessage)
		
		def rabbitSent=0		
		
		eventService.metaClass.rabbitSend = {queue, msg -> rabbitSent++; println queue}
		eventService.springSecurityService = this.springSecurityService
		def message = new Message(title:"new messege", content:"send to rabbitMQ send to rabbitMQ send to rabbitMQ send to rabbitMQ test!", createdDate:new Date())
		
		eventService.sendMessage(eventInstance.id, message)
		
		assertEquals 5, rabbitSent
		assertEquals 2, eventInstance.messages.size()
		assertEquals 5, MessageLog.count()
		assertEquals 2, MessageLog.get(1).concatinationSize
	}
	
	void testSendGroupChatMessage(){
		mockDomain(Gateway,[new Gateway(prefix:'00', name:'inter_clickatell', queueName:'openmessenger', createdBy:'admin'),
			new Gateway(prefix:'66', name:'th_dtac', queueName:'openmessenger_dtac', createdBy:'admin')])
		
		def eventInstance = new Event(name: 'The Championships, Wimbledon',
			description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
			occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
			status:Status.NORMAL, type:Type.GROUP_CHAT)
		 
		/*def subscribers = [	new Subscriber(msisdn: '66809737791', active: 'Y'),
							 new Subscriber(msisdn: '66809737792', active: 'Y'),
							new Subscriber(msisdn: '66809737793', active: 'Y'),
							new Subscriber(msisdn: '66809737794', active: 'Y'),
							new Subscriber(msisdn: '66809737795', active: 'Y')	]*/
		def subscribers = [	'62809737791', '66809737792',
			'66809737793', //'66809737794',
			'885809737795']
		def initMessage = new Message(title:"title", content:"first content", createdDate:new Date())
		
		mockDomain(Event, [eventInstance])
		mockDomain(Subscriber, subscribers)
		mockDomain(MessageLog)
		
		def eventService = new EventService()
		subscribers.each {
			eventService.subscribeToEvent(eventInstance.id, it)
		}
		
		//eventInstance.subscribers = subscribers
		eventInstance.addToMessages(initMessage)
		eventInstance.save()
		
		def rabbitSent=0
		
		eventService.metaClass.rabbitSend = {queue, msg -> rabbitSent++; println queue}
		def message = new Message(title:"new messege", content:"send to rabbitMQ", createdDate:new Date(), createBy:'66809737791')
		
		eventService.sendGroupChatMessage(eventInstance.id, message, 'default Sender')
		
		assertEquals 4, rabbitSent
		assertEquals 2, eventInstance.messages.size()
	}
	
}
