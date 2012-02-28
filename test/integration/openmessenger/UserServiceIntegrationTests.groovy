package openmessenger

import java.text.SimpleDateFormat
import grails.test.*
import openmessenger.Event.Status
import openmessenger.Event.Type

class UserServiceIntegrationTests extends GroovyTestCase {
	def userService
	def eventIds
	def roleIds
	
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
		
		def user = new User(username:'user', password:'password', firstname:'firstname'
			, lastname:'lastname', email:'email@email.com', enabled:true
			, accountExpired:false, accountLocked:false, passwordExpired:false)
		user.save()
		
		eventIds = []
		eventInstances.each { 
			it.save()
			eventIds.add(it.id)
		}
		eventIds.sort()
		def minusLastIds = eventIds.minus(eventIds.last())
		minusLastIds.each {
			UserEvent.create(user, Event.get(it))
		}
		
		def roles = [new Role(authority:'auth1'),
			new Role(authority:'auth2'),
			new Role(authority:'auth3'),
			new Role(authority:'auth4')]
		roleIds = []
		roles.each {
			it.save()
			roleIds.add(it.id)
		}
		roleIds.sort()
		def minusLastRoleIds = roleIds.minus(roleIds.last())
		minusLastRoleIds.each {
			UserRole.create(user, Role.get(it))
		}
    }

    protected void tearDown() {
        super.tearDown()
		eventIds.clear()
		roleIds.clear()
    }

    void testSaveEvents() {
		def updateEvents = eventIds.minus(eventIds.first())
		def user = User.findByUsername('user')
		
		assert eventIds.minus(eventIds.last()) == user.events*.id.sort()
		userService.saveEvents(user, updateEvents)
		assert updateEvents == user.events*.id.sort()	
	}
	
	void testSaveRoles() {
		def updateRoles = roleIds.minus(roleIds.first())
		def user = User.findByUsername('user')
		
		assert roleIds.minus(roleIds.last()) == user.authorities*.id.sort()
		userService.saveRoles(user, updateRoles)
		assert updateRoles == user.authorities*.id.sort()
	}
	
	void testSaveUser() {
		def user = new User(username:'newUser', password:'password', firstname:'username'
			, lastname:'lastname', email:'email@email.com', enabled:true
			, accountExpired:false, accountLocked:false, passwordExpired:false)
		def events = eventIds.minus(eventIds.first())
		def roles = roleIds.minus(roleIds.first())
		
		userService.save(user, roles, events)
		
		assert 3 == user.authorities.size()
		assert 3 == user.events.size()
		
		assert roles == user.authorities*.id.sort()
		assert events == user.events*.id.sort()
	}
	
	void testUpdateUser() {
		def events = eventIds.minus(eventIds.first())
		def roles = roleIds.minus(roleIds.first())
		def user = User.findByUsername('user')
		
		userService.save(user, roles, events)
		
		assert 3 == user.authorities.size()
		assert 3 == user.events.size()
		
		assert roles == user.authorities*.id.sort()
		assert events == user.events*.id.sort()
	}
	
	void testDeleteUser() {
		def user = User.findByUsername('user')
		userService.delete(user)
		
		assert null == User.findByUsername('user')
	}
}
