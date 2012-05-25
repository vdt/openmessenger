package openmessenger

import grails.test.*
import java.text.SimpleDateFormat
import openmessenger.Event.Status
import openmessenger.Event.Type
import openmessenger.User

class EventControllerTests extends ControllerUnitTestCase {

    def eventService
    def springSecurityService

    protected void setUp() {
        super.setUp()
		mockConfig ('''
		openmessenger.eventCallback="eventCallback"
		''')
		
		springSecurityService = new HashMap()
		springSecurityService.principal = new HashMap()
		springSecurityService.principal.username = 'default username'
		springSecurityService.principal.id = 1
		
		def firstEvent = new Event(name: 'The Championships, Wimbledon',
			description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
			occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
			type:Type.GROUP_CHAT,
			status: Status.NORMAL)
			
		def secondEvent = new Event(name: 'The Australian Open',
			description: 'The tournament is held in the middle of the Australian summer, in the last fortnight of the month of January; thus an extreme-heat policy is put into play when temperatures reach dangerous levels.',
			occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
			type:Type.GROUP_CHAT,
			status: Status.NORMAL)
			
		mockDomain(Event, [firstEvent, secondEvent])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testListAllEvents() {
		def user = new User(username:'user', password:'password', firstname:'firstname'
			, lastname:'lastname', email:'email@email.com', enabled:true
			, accountExpired:false, accountLocked:false, passwordExpired:false)
		mockDomain(User, [user])
		
		def eventControl = mockFor(EventService)
		eventControl.demand.findAllEventByUser(1..1) { param -> Event.list() }
		controller.eventService = eventControl.createMock()
		controller.springSecurityService = springSecurityService
		
        def events =  controller.listAllEvents()
        assertNotNull events
		
        assertEquals "listAllEvents", controller.renderArgs.view
        
    }

    void testViewEmptyEvent(){      
		mockDomain(Message)
		
		def eventControl = mockFor(EventService)
		eventControl.demand.findEventById(1..1) { id -> null }
		eventControl.demand.getEventMessages(0..0) { messages, offset, max -> messages?.subList(offset, offset+max) }
		controller.eventService = eventControl.createMock()
		
		//controller.eventService = eventControl.createMock() 

        controller.params.id = 1
        controller.view()
        assertEquals "view", controller.renderArgs.view	  

        eventControl.verify()    
    }  
	
	void testViewEvent(){
		def messages = [new Message(title: "the title1", content: "the content1", createdDate: new Date()),
			new Message(title: "the title2", content: "the content2", createdDate: new Date())
			]

		mockDomain(Message, messages)		
		
		def firstEvent = Event.findByName('The Championships, Wimbledon')
		messages.each {
			firstEvent.addToMessages(it)
			}		
		
		def eventControl = mockFor(EventService)
		eventControl.demand.findEventById(1..1) { id -> firstEvent }
		eventControl.demand.getEventMessages(1..1) { messagelist, offset, max -> messages?.subList(offset, offset+max) }
		controller.eventService = eventControl.createMock()
		
		//controller.eventService = eventControl.createMock()

		controller.params.id = 1
		controller.view()
		assertEquals "view", controller.renderArgs.view
		assertEquals 2, controller.renderArgs.model.messages.size()

		eventControl.verify()
	}

	void testListEventSubscriber(){
  		def eventControl = mockFor(EventService)          
		eventControl.demand.findEventById(1..1) {->true}   
		this.controller.eventService = eventControl.createMock()         
		controller.eventService = eventControl.createMock() 

        controller.params.id = "1"
        controller.listEventSubscribers()
        assertEquals "listEventSubscribers", controller.renderArgs.view	  

        eventControl.verify()  	
		
	}
               
    void testCreateEvent(){
		def eventInstance = controller.create()
		assertNotNull eventInstance
	
	}
	

    void testSubscribeToEvent(){     
        def eventControl = mockFor(EventService) 
        eventControl.demand.subscribeToEvent(1..1) {->true}
        controller.params.eventId = "2"
        controller.params.msisdn = "1234567890"
        this.controller.eventService = eventControl.createMock()
        
        controller.subscribeToEvent()
        
        assertEquals "listEventSubscribers", controller.redirectArgs["action"]  
        eventControl.verify()
    }

    void testSendMessage(){
        def eventService = mockFor(EventService)
        eventService.demand.sendMessage(1..1) {->true}
        controller.params.eventId = "2"
        controller.params.message = "test message"

        this.controller.eventService = eventService.createMock()

        controller.sendMessage()

        assertEquals "view", controller.redirectArgs["action"]
        eventService.verify()
    }

    void testSendMessageWithLongMessage() {
    	def message = "test messageasdfasjdflasjfjasldfjasdfjklasdjfklasdjfaklsdjfklasdjfklasdfljasdklfjsdfjasdfjklasdjflasdfjlasdjflasdjflasdjfkljasdfjsdafljasdlfjasdlfjasdlfaslfjasldfjasldjfasldfjasldfjasldfjasdlfjlasdfjasdkljfaklsdjfl;asdfjasdlfjasldjflasdasdfasdfasdfasdfasdf"
    	controller.params.eventId = "2"
        controller.params.message = message

        controller.sendMessage()

        assertEquals "view", controller.redirectArgs["action"]

        assert message == controller.redirectArgs.params.errorMessage
    }
	
	void testEditEvent() {
		def firstEvent = Event.findByName('The Championships, Wimbledon')
		controller.params.id = firstEvent.id		
		controller.edit()	
		def modelAndView = controller.modelAndView	
		assert "edit" == controller.renderArgs.view
		assert firstEvent.name == controller.renderArgs.model.eventInstance.name
		
		
		controller.params.id = "5"		
		controller.edit()
		assert "home" == controller.redirectArgs.controller
	}
}
