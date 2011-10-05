package openmessenger

import openmessenger.Event.Type

class EventService {

    static transactional = true
    def queueName = 'openmessenger'

    def findEventById(Long eventId){
        def eventInstance = Event.get(eventId)
    }

	def getEventMessages(def messages, Integer offset, Integer max = 10){
		messages.subList(offset, offset+max)
	}
	
    def findAllEventByNameLikeKeyword(String keyword) {
        Event.findAllByNameLike('%'+keyword+'%')
    }
    
    def findAllEventByStatus(def status){
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

    def sendMessage(Long eventId, Message message, String senderId=null){
        def event = Event.findById(eventId)
		def isSenderId = event.isSenderId
		def content
		if(event.type==Type.GROUP_CHAT){
			content = "${message.createBy}: ${message.content}"
		}else{
			content = message.content
		}
		message.title = "News from "+ event.name
        event.addToMessages(message)
		event.subscribers.each {
            def msg = [msisdn:it.msisdn, content:content, date:new Date(), isSenderId:isSenderId, senderId:senderId]
			rabbitSend(queueName, msg)
        }		
        event.save()
    }                                                           
}
