package openmessenger

class EventService {

    static transactional = true

    def findAllEventByNameLikeKeyword(String keyword) {
        Event.findAllByNameLike('%'+keyword+'%')
    }
    
    def findAllEventByStatus(String status){
        Event.findAllByStatus(status)
    }
    
    def subscribeToEvent(Integer eventId, String msisdn){
        //println "dude"
        def event = Event.get(eventId)
        def subscriber = Subscriber.findByMsisdn(msisdn)
        
        event.addToSubscribers(subscriber)
        event.save()
    }
    
}
