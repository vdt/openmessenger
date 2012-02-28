package openmessenger

import java.text.SimpleDateFormat
import grails.test.*
import openmessenger.Event.Status
import openmessenger.Event.Type

class UserServiceTests extends GrailsUnitTestCase {
	def userService
    protected void setUp() {
        super.setUp()
		
		userService = new UserService()
		
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
	
		def users = [new User(username:'user',
			password:'password',
			firstname:'firstname',
			lastname:'lastname',
			email:'email@email.com',
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
		
		mockDomain(UserEvent)		
		
		(1..3).each {
			UserEvent.create(User.get(1), Event.get(it), true)
		}
		
		def roles = [new Role(authority:'auth1'),
					new Role(authority:'auth2'),
					new Role(authority:'auth3'),
					new Role(authority:'auth4')]
		mockDomain(Role, roles)
		
		mockDomain(UserRole)
		(1..3).each {
			UserRole.create(User.get(1), Role.get(it), true)
		}
    }

    protected void tearDown() {
        super.tearDown()
    }
		
	void testIsUpdate() {
		def updateEvents = [2,3,4]
		def user = User.findByUsername('user')
		
		assert true == userService.isUpdate("events", user, updateEvents)
		
		updateEvents = [1,2,3]
		assert false == userService.isUpdate("events", user, updateEvents)
	}
	
	void testIsEventUpdate() {
		def updateEvents = [2,3,4]
		def user = User.findByUsername('user')
		
		assert true == userService.isEventUpdate(user, updateEvents)
		
		updateEvents = [1,2,3]
		assert false == userService.isEventUpdate(user, updateEvents)
	}
	
	void testIsRoleUpdate() {
		def updateEvents = [2,3,4]
		def user = User.findByUsername('user')
		
		assert true == userService.isRoleUpdate(user, updateEvents)
		
		updateEvents = [1,2,3]
		assert false == userService.isRoleUpdate(user, updateEvents)
	}
	
	void testFindUpdateItems() {
		def updateEvents = [2,3,4]
		def user = User.findByUsername('user')
		
		def events = userService.findUpdateItems("events", user, updateEvents)
		def deprecateItems = events.deprecateItems
		def newItems = events.newItems
		
		assert [1] == deprecateItems
		assert [4] == newItems
		
		
		def emptyEventUser  = User.findByUsername('boytwo')
		
		events = userService.findUpdateItems("events", emptyEventUser, updateEvents)
		deprecateItems = events.deprecateItems
		newItems = events.newItems
		
		assert [] == deprecateItems
		assert [2,3,4] == newItems
	}
	
	void testFindUpdateEvents() {
		def updateEvents = [2,3,4]
		def user = User.findByUsername('user')
		
		def events = userService.findUpdateEvents(user, updateEvents)
		def deprecateItems = events.deprecateItems
		def newItems = events.newItems
		
		assert [1] == deprecateItems
		assert [4] == newItems
	}
	
	void testGetUpdateRoles() {
		def updateRoles = [2,3,4]
		def user = User.findByUsername('user')
		
		def events = userService.findUpdateRoles(user, updateRoles)
		def deprecateItems = events.deprecateItems
		def newItems = events.newItems
		
		assert [1] == deprecateItems
		assert [4] == newItems
	}
}
