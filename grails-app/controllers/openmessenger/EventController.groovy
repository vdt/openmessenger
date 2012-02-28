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
		def total = targetEvent?.messages?.size()?:0
		if(offset+max>total && total > 0) {
			max =total-offset
		}
		def messages = null
		if(total>0) {
			messages = eventService.getEventMessages(targetEvent?.messages?.toList(), offset, max)
		}
		
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
        def eventInstance = new Event()        
        return [eventInstance: eventInstance]
    }	

    def save = {
        def eventInstance
        def eventType = params.type
		if(params.occuredDate) {
			params.occuredDate = new SimpleDateFormat(message(code:'default.stringdate.format')).parse(params.occuredDate)
		}
        //println "group "+eventType
        //println "params: "+params
        if(eventType==Type.EVENT.toString()){    
            eventInstance = new Event(params)            
        }else if(eventType==Type.GROUP_CHAT.toString()){    
            eventInstance = new GroupChat(params)
        }
		
        eventInstance.validate()
        
        if(eventInstance.hasErrors()){
            log.error eventInstance.errors
        }

        if (eventInstance.save(flush: true)){
			def userDetails = springSecurityService.principal
			def user = User.get(userDetails.id)
			UserEvent.create(user, eventInstance)
            redirect(action: "view", id: eventInstance.id)
        }else{
            render(view: "create", model: [eventInstance: eventInstance])
        }
    }      
	
	def edit = {
		def eventInstance = Event.get(params.id)
		if(eventInstance) {
			render(view: "edit", model: [eventInstance: eventInstance])
		} else {
			redirect(controller:"home", action: "main")
		}
	}
	
	def update = {
		def eventInstance = Event.get(params.id)
		if(params.occuredDate) {
			params.occuredDate = new SimpleDateFormat(message(code:'default.stringdate.format')).parse(params.occuredDate)
		}
		if (eventInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (eventInstance.version > version) {
					eventInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'event.label', default: 'Event')] as Object[], "Another user has updated this User while you were editing")
					render(view: "edit", model: [eventInstance: eventInstance])
					return
				}
			}
			eventInstance.properties = params
			if (eventInstance.validate()) { 
				try {
					eventInstance.save()					
					flash.message = "${message(code: 'default.updated.message', args: [message(code: 'event.label', default: 'Event'), eventInstance.id])}"
					redirect(action: "view", id: eventInstance.id)
				} catch (Exception e) {
					log.error(e)
					render(view: "edit", model: [eventInstance: eventInstance])
				}
			}
			else {
				log.error(eventInstance.errors)
				render(view: "edit", model: [eventInstance: eventInstance])
			}
		}
		else { println 'not found target instance'
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'event.label', default: 'Event'), params.id])}"
			redirect(action: "listAllEvents")
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

