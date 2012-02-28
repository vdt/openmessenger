package openmessenger

import grails.test.*
import java.text.SimpleDateFormat
import openmessenger.Event.Status
import openmessenger.Event.Type

class HomeControllerTests extends ControllerUnitTestCase {
    
	protected void setUp() {
		super.setUp()
		mockConfig ('''
		openmessenger.eventCallback="eventCallback"
		''')
		
		def users = [new User(username:'boyone',
				password:'password',
				firstname:'boyone',
				lastname:'lastname',
				email:'boyone@email.com',
				enabled:true,
				accountExpired:false,
				accountLocked:false,
				passwordExpired:false
				),
				new User(username:'boytwo',
				password:'password',
				firstname:'boytwo',
				lastname:'lastname',
				email:'email@email.com',
				enabled:true,
				accountExpired:false,
				accountLocked:false,
				passwordExpired:false
			)]

			mockDomain(User, users)
			
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

    void testMain() {
		def eventService = mockFor(EventService)
		eventService.demand.findAllEventByUser(1..1) { user -> Event.list()}
		def springSecurityService = [principal:[userDetails:[id:1]]]
		
		controller.eventService = eventService.createMock()
		controller.springSecurityService = springSecurityService
		
		controller.main()
		
		assert "main" == controller.renderArgs.view
		assert 0 == controller.renderArgs.model.events.size()
		assert 2 == controller.renderArgs.model.groupChats.size()
		assert 2 == controller.renderArgs.model.users.size()
		
		eventService.verify()
    }
}
