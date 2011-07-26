package openmessenger

class EventService {

    static transactional = true
    def queueName = 'openmessenger'

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

    def sendMessage(Long eventId, Message message){
        println "sendMessage EventService:"+eventId
        def event = Event.findById(eventId)
        println event.name
        event.addToMessages(message)
        event.subscribers.each {
            def msg = [msisdn:it.msisdn, content:message.content, date:new Date()]
            rabbitSend(queueName, msg)
        }				
        event.save()
    }
                                                           
}
