package openmessenger

class EventService {

    static transactional = true

    def findEventById(Long eventId){
		Event.get(eventId)
	}

	def findAllEventByNameLikeKeyword(String keyword) {
        Event.findAllByNameLike('%'+keyword+'%')
    }
    
    def findAllEventByStatus(String status){
        Event.findAllByStatus(status)
    }
    
    def subscribeToEvent(Long eventId, String msisdn){
        def event = Event.get(eventId)
        def subscriber = Subscriber.findByMsisdn(msisdn)
        
        event.addToSubscribers(subscriber)
        event.save()
    }         

	def unsubscribeFromEvent(Long eventId, String msisdn){
    	def event = Event.get(eventId) 	
		def targetSubscriber = event.subscribers.find{it.msisdn=msisdn}
		event.removeFromSubscribers(targetSubscriber)
		event.save()
	}
                                                           
}
