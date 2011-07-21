package openmessenger

class EventController {
    
    def eventService
    
	def view = {
		def targetEvent = Event.get(params.id)
		render(view: "view", model:[event: targetEvent])
	}

    def listAllEvents = { 
        def events = Event.list()
        render(view:"listAllEvents",model:[events: events])
    }

    def save = {
        def eventInstance = new Event(params)
        if (eventInstance.save(flush: true)){
            redirect(action: "view", id: eventInstance.id)
        }else{
            render(view: "create", model: [eventInstance: eventInstance])
        }
    }   
    
    def subscribeToEvent = {
        def eventId =  params.eventId
        def msisdn =  params.msisdn
        
        eventService.subscribeToEvent(eventId, msisdn)
        
        redirect(action: "show", id: eventId)
    }

	def sendMessage = {
		def eventId = params.eventId
		def message = params.message

		eventService.sendMessage(eventId, message)

		redirect(action: "show", id: eventId)
	}
}
