package openmessenger

import grails.test.*

class UserEventIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testRemoveAllByEvent() {		
		def user = User.get(1)
		def events = Event.list()
		events.each {
			UserEvent.create(user, it, true)
		}
		def allSize = events.size()
		assert allSize == user.events.size()
		
		def event = Event.get(1)
		UserEvent.removeAll(event)
		
		assert allSize - 1 == user.events.size()
    }
	
	void testRemoveAllByUser() {
		def user = User.get(1)
		def events = Event.list()
		events.each {
			UserEvent.create(user, it, true)
		}
		def allSize = events.size()
		assert allSize == user.events.size()
		
		UserEvent.removeAll(user)
		
		assert 0 == user.events.size()
	}
	
	void testRemove() {
		def user = User.get(1)
		def events = Event.list()
		events.each {
			UserEvent.create(user, it, true)
		}
		
		def allSize = user.events.size()
		def event = Event.get(1)
		UserEvent.remove(user, event)
		
		assert allSize - 1 == user.events.size()
	}
}
