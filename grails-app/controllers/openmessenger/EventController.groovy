package openmessenger

import openmessenger.Event.Type
import java.text.SimpleDateFormat

class EventController {
    
    def eventService
	def springSecurityService	
	
	def view = {
        def targetEvent = eventService.findEventById(Long.valueOf(params.id))		
		def offset = params.offset?params.int('offset'):0
		def max = params.max?params.int('max'):10
		def total = targetEvent.messages.size()
		if(offset+max>total) max =total-offset
		def messages = eventService.getEventMessages(targetEvent.messages.toList(), offset, max)
		render(view: "view", model:[event: targetEvent, total:total, messages:messages, offset:offset]) //, total:total, messages:messages
    }

    def listAllEvents = { 
		def userDetails = springSecurityService.principal
		def user = User.get(userDetails.id)		
		def events = eventService.findAllEventByUser(user)
        //def events = Event.list()
        render(view:"listAllEvents",model:[events: events])
    }       

	def listEventSubscribers = {
	    def targetEvent = eventService.findEventById(Long.valueOf(params.id))
        render(view: "listEventSubscribers", model:[event: targetEvent]) 
	}    
	
    def create = {
        def eventInstance
        def eventType = params.type
        if(eventType=='event'){
            eventInstance = new Event()    
        }else if(eventType=='groupChat'){
            eventInstance = new GroupChat()
        }
        //eventInstance.properties = params
        return [eventInstance: eventInstance, eventType:eventType]
    }	

    def save = {
        def eventInstance
        def eventType = params.type
		if(params.occuredDate) {
			params.occuredDate = new SimpleDateFormat(message(code:'default.stringdate.format')).parse(params.occuredDate)
		}
        //println "group "+eventType
        //println "params: "+params
        if(eventType=='event'){    
            eventInstance = new Event(params)
            eventInstance.type = Type.EVENT
            //println 'add event'
        }else if(eventType=='groupChat'){    
            eventInstance = new GroupChat(params)
            eventInstance.type = Type.GROUP_CHAT
            //println "add group ${eventInstance.codename}"
        }
		
        eventInstance.validate()
        
        if(eventInstance.hasErrors()){
            eventInstance.errors.each {
                //println it
            }
        }

        if (eventInstance.save(flush: true)){
			def userDetails = springSecurityService.principal
			def user = User.get(userDetails.id)
			UserEvent.create(user, eventInstance)
            redirect(action: "view", id: eventInstance.id)
        }else{
            render(view: "create", model: [eventInstance: eventInstance, , eventType:eventType])
        }
    }      
           
    def unsubscribeFromEvent = {
        def eventId =  params.id
        def msisdn =  params.msisdn  
        
        eventService.unsubscribeFromEvent(Long.valueOf(eventId), msisdn)
        
        redirect(action: "listEventSubscribers", id: eventId)
    }

    
    def subscribeToEvent = {
        def eventId =  params.eventId
        def msisdn =  params.msisdn      
   
        eventService.subscribeToEvent(Long.valueOf(eventId), msisdn)
        
        redirect(action: "listEventSubscribers", id: eventId)
    }

    def sendMessage = {       
        def eventId = params.eventId
        def content = params.message
		//TODO check subscriber before sent and create msg
		if(eventId && content) {
			def message = new Message(title:"News from openmessenger", content: content, createdDate: new Date())
			eventService.sendMessage(Long.valueOf(eventId), message)
		}

        redirect(action: "view", id: eventId)
    }
}

