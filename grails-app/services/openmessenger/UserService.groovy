package openmessenger

class UserService {

    static transactional = true

    def getUserByEnabled(Boolean enabled){
		User.findAllByEnabled(enabled)
	}
	
	def getUser(Long userId) {
		User.get(userId)
	}
	
	def isUpdate(String type, User user, List updateEvents) {
		def events = user."$type"*.id.sort()
		return events != updateEvents.sort()
	}
	
	def isEventUpdate(User user, List updateEvents) {
		return isUpdate("events", user, updateEvents)
	}
	
	def isRoleUpdate(user, updateEvents) {
		return isUpdate("authorities", user, updateEvents)
	}
	
	def findUpdateItems(String type, User user, List updateItems) {
		def items = user."$type"*.id.sort()
		updateItems.sort()
		def newItems = updateItems - items
		def deprecateItems = items - updateItems
		return [deprecateItems: deprecateItems, newItems: newItems]
	}
	
	def findUpdateEvents(user, updateEvents) {
		return findUpdateItems("events", user, updateEvents)
	}
	
	def findUpdateRoles(user, updateEvents) {
		return findUpdateItems("authorities", user, updateEvents)
	}
	
	def saveEvents(User user, List updateEvents) {
		def events = findUpdateEvents(user, updateEvents)
		events.newItems.each {
			UserEvent.create(user, Event.get(it))
		}
		events.deprecateItems.each {
			UserEvent.remove(user, Event.get(it))
		}
	}
	
	def saveRoles(User user, List updateRoles) {
		def events = findUpdateRoles(user, updateRoles)
		events.newItems.each {
			UserRole.create(user, Role.get(it))
		}
		events.deprecateItems.each {
			UserRole.remove(user, Role.get(it))
		}					
	}
	
	def save(User user, List roles, List events) {
		user.save()
		saveRoles(user, roles)
		saveEvents(user, events)
		//return user		
	}	
	
	def delete(User user) {
		user.authorities.each {
			UserRole.remove(user, it)
		}
		user.events.each {
			UserEvent.remove(user, it)
		}
		user.delete()		
	}
}
