package openmessenger

import java.text.SimpleDateFormat
import grails.test.*
import openmessenger.Event.Status
import openmessenger.Event.Type

class UserEventTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
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
		
		def user = new User(username:'user', password:'password', firstname:'firstname'
			, lastname:'lastname', email:'email@email.com', enabled:true
			, accountExpired:false, accountLocked:false, passwordExpired:false)
		mockDomain(User, [user])		
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	void testCreate() {
		mockDomain(UserEvent)
		def user = User.get(1)
		def events = Event.list()
		events.each {
			UserEvent.create(user, it, true)
		}
		assert events.size() == user.events.size()
	}
	
	void testGet() {
		mockDomain(UserEvent)
		def user = User.get(1)
		def events = Event.list()
		events.each {
			UserEvent.create(user, it, true)
		}
		def event = Event.get(1)
		def userEvent = UserEvent.get(user.id, event.id)
		assert userEvent != null
		assert user.username == 'user'
		assert userEvent.user.username == user.username
		
		assert event.name == 'The Championships, Wimbledon'
		assert userEvent.event.name == event.name
	}	    
}
