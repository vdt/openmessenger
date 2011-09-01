package openmessenger

class EventService {

    static transactional = true
    def queueName = 'openmessenger'

    def findEventById(Long eventId){
        Event.get(eventId)
    }

	def getEventMessages(def messages, Integer offset, Integer max = 10){
		messages.subList(offset, offset+max)
	}
	
    def findAllEventByNameLikeKeyword(String keyword) {
        Event.findAllByNameLike('%'+keyword+'%')
    }
    
    def findAllEventByStatus(String status){
        Event.findAllByStatus(status)
    }
	
	def findAllEventByUser(User user){
		def userEvents = UserEvent.findAllByUser(user)
		userEvents*.event.sort {it.name}
	}
    
    def subscribeToEvent(Long eventId, String msisdn){
        def event = Event.get(eventId)
        def subscriber = Subscriber.findByMsisdn(msisdn)     
		if(subscriber==null) subscriber = new Subscriber(msisdn: msisdn, active:"Y")
        
        event.addToSubscribers(subscriber)
        event.save()
    }         

    def unsubscribeFromEvent(Long eventId, String msisdn){
    	def event = Event.get(eventId) 	
        def targetSubscriber = event.subscribers.find{it.msisdn==msisdn}      
		println targetSubscriber.msisdn
        event.removeFromSubscribers(targetSubscriber)
        event.save()
    }

    def sendMessage(Long eventId, Message message){
        def event = Event.findById(eventId)
		message.title = "News from "+ event.name
        event.addToMessages(message)
        event.subscribers.each {
            def msg = [msisdn:it.msisdn, content:message.content, date:new Date()]
            rabbitSend(queueName, msg)
        }		
        event.save()
    }
                                                           
}
