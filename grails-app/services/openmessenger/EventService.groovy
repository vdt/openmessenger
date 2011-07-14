package openmessenger

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class EventService {

    static transactional = true
	def rabbitTemplate
	def queueName

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

	def sendMessage(Long eventId, String message){
		def event = Event.get(eventId) 	
		event.subscribers.each {
			def msg = [msisdn:it.msisdn, msg:message, date:new Date()]
			rabbitTemplate.convertAndSend(queueName, message)
		}		
		event.addToMessages(new Message(title:message, content:message, createdDate:new Date()))
		event.save()
	}
                                                           
}
