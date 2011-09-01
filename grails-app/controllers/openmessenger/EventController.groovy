package openmessenger

class EventController {
    
    def eventService
	def springSecurityService
    
    def view = {
        def targetEvent = eventService.findEventById(Long.valueOf(params.id))
		/*def offset = params.offset?params.int('offset'):0
		def max = params.max?params.int('max'):10
		def total = targetEvent.messages.size()
		if(offset+max>total) max =total-offset
		def messages = eventService.getEventMessages(targetEvent.messages.toList(), offset, max)*/
		render(view: "view", model:[event: targetEvent]) //, total:total, messages:messages
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
        //eventInstance.properties = params
        return [eventInstance: eventInstance]
    }	

    def save = {
        def eventInstance = new Event(params)
        if (eventInstance.save(flush: true)){
            redirect(action: "view", id: eventInstance.id)
        }else{
            render(view: "create", model: [eventInstance: eventInstance])
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
        def message = new Message(title:"News from openmessenger", content: content, createdDate: new Date())
        eventService.sendMessage(Long.valueOf(eventId), message)

        redirect(action: "view", id: eventId)
    }
}

