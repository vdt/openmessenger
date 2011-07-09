package openmessenger

class EventController {
    def listAllEvents = { 
        def events = Event.list()
        render(template:"listAllEvents",model:[events: events])
    }

    def save = {
        def eventInstance = new Event(params)
        if (eventInstance.save(flush: true)){
            redirect(action: "show", id: eventInstance.id)
        }else{
            render(view: "create", model: [eventInstance: eventInstance])
        }
    }   
}
