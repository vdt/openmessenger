package openmessenger

import openmessenger.Event.Type
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class EventService {

    static transactional = true
	def springSecurityService
	def subscriberFileService
    //def queueName = 'openmessenger'
	def callbackQueue = CH.config.openmessenger.eventCallback	

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
		println subscriber
		if(subscriber==null){ 
			subscriber = new Subscriber(msisdn: msisdn, active:"Y")	
			subscriber.save()		
		}
        
        event.addToSubscribers(subscriber)
        event.save()
		
		def gateway = findGateway(subscriber.msisdn)
		gateway.addToSubscribers(subscriber)
		
		gateway.save()
    }  
	
	def subscribeToEvent(Long eventId, File file){
		def msisdns = subscriberFileService.parseCsv(file)
		def event = Event.get(eventId)
		msisdns.each {
			def subscriber = Subscriber.findByMsisdn(it)
			if(subscriber==null) subscriber = new Subscriber(msisdn: it, active:"Y")			
			event.addToSubscribers(subscriber)
			if(!subscriber.gateway){
				def gateway = findGateway(subscriber.msisdn)
				gateway.addToSubscribers(subscriber)
				gateway.save()
			}
		}
		event.save()				
	}
	
	def findGateway(String msisdn){
		log.debug(msisdn)
		def prefix
		def gateway
		def prefixSize = CH.config.openmessenger.prefixSize
		log.debug("find gateway : $msisdn")
		for(;0 < prefixSize; prefixSize--) {
			prefix = msisdn.substring(0, prefixSize)
			gateway = Gateway.findByPrefix(prefix)
			log.debug("$prefix : ${gateway?.name}")	
			if(gateway)	return gateway	
		}		
		
		if(!gateway){
			gateway = Gateway.findByPrefix('00')
		}
		log.debug("gateway $msisdn $gateway.name")
		return gateway
	}

    def unsubscribeFromEvent(Long eventId, String msisdn){
    	def event = Event.get(eventId) 	
        def targetSubscriber = event.subscribers.find{it.msisdn==msisdn}      
		println targetSubscriber.msisdn
        event.removeFromSubscribers(targetSubscriber)
        event.save()
    }

    def sendPersonalMessage(Long eventId, def username, def msisdn, Message message) {
    	def event = Event.findById(eventId)
    	def isSenderId = event.isSenderId
    	if(!event.subscribers.find {it.msisdn == msisdn}) {
    		subscribeToEvent(event.id, msisdn)
    	}
    	println msisdn
    	def subscriber = Subscriber.findByMsisdn(msisdn)
    	println "$subscriber ${subscriber?.msisdn} ${subscriber?.gateway}"
    	message.title = "News from ${event.name} to {msisdn}"
		message.createBy = username
        event.addToMessages(message)
        def date = new Date()
        def msg = [msisdn:msisdn, content:message.content, date:date, isSenderId:isSenderId, eventId:eventId, callbackQueue:callbackQueue, isUnicode:event.isUnicode]
			insertMessageLog(event, event.type, msisdn, subscriber.gateway, message.content, message.createBy, date)
			rabbitSend(subscriber.gateway.queueName, msg)
		event.save()
    }

    def sendMessage(Long eventId, Message message){
		def userDetails = springSecurityService.principal
		log.debug("create by: ${userDetails?.username}, eventId: $eventId")
        def event = Event.findById(eventId)
		def isSenderId = event.isSenderId
		message.title = "News from "+ event.name
		message.createBy = userDetails?.username
        event.addToMessages(message)
		event.subscribers.each {
			log.debug(it.msisdn)
			def date = new Date()
            def msg = [msisdn:it.msisdn, content:message.content, date:date, isSenderId:isSenderId, eventId:eventId, callbackQueue:callbackQueue, isUnicode:event.isUnicode]
			insertMessageLog(event, event.type, it.msisdn, it.gateway, message.content, message.createBy, date)
			rabbitSend(it.gateway.queueName, msg)
			//rabbitSend(queueName, msg)
        }		
        event.save()
    } 
	
	def sendGroupChatMessage(Long eventId, Message message, String senderId=null){
		def event = Event.findById(eventId)
		def subscriber = Subscriber.findByMsisdn(message.createBy)
		def isSenderId = event.isSenderId
		def content = "${message.createBy}: ${message.content}"
		def subscribers	= event.subscribers - subscriber
		message.title = "News from "+ event.name
		event.addToMessages(message)
		subscribers.each {
			log.debug(it.msisdn)
			def date = new Date()
			def msg = [msisdn:it.msisdn, content:content, date:date, isSenderId:isSenderId, senderId:senderId, eventId:eventId, callbackQueue:callbackQueue, isUnicode:event.isUnicode]
			insertMessageLog(event, event.type, it.msisdn, it.gateway, message.content, message.createBy, date)
			rabbitSend(it.gateway.queueName, msg)
			//rabbitSend(queueName, msg)
		}
		event.save()
	}
	
	private void insertMessageLog(Event event, Type eventType, String msisdn, Gateway gateway, String msg, String createBy, Date date){ 
		def msgLog = new MessageLog(event:event, eventType:eventType, msisdn:msisdn, gateway:gateway.name, price:gateway.rate, msg:msg, createBy:createBy, date:date)
		msgLog.concatinationSize = OpenMessengerUtil.getConcatinationSize(msg, gateway.messageSize)
		msgLog.save()
	}
	
	
}
