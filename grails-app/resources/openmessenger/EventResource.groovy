package openmessenger
import static org.grails.jaxrs.response.Responses.*
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response
import javax.ws.rs.PathParam
import openmessenger.EventDTO


@Path('/api/event')
class EventResource {
    def eventService
    def communicationService

    @GET
    @Path('/{id:[a-z][a-z_0-9]{1,7}}/msisdn/{msisdn}/{senderId}/{content}/passphase/{username}/{password}')
    @Produces('text/plain')
    Response sendMessageToEvent(@PathParam('id') String id,
                                  @PathParam('msisdn') String msisdn,
								  @PathParam('senderId') String senderId,
								  @PathParam('content') String content,
								  @PathParam('username') String username,
								  @PathParam('password') String password) 
	{
		content = URLDecoder.decode(content, 'UTF-8')
		def messageMap = communicationService.extractMessage(id, msisdn, content)    
        eventService.sendGroupChatMessage(messageMap.eventId, messageMap.message, senderId)
        ok "Request Completed"
    }

    @POST
    @Produces('text/plain')
    Response postMessageToEvent(EventDTO eventDTO){
        println "${eventDTO.codename} and ${eventDTO.msisdn} and ${eventDTO.senderId} and ${eventDTO.content} and ${eventDTO.username} and ${eventDTO.password}"
        def messageMap = communicationService.extractMessage(eventDTO.codename, eventDTO.msisdn, eventDTO.content)    
        eventService.sendGroupChatMessage(messageMap.eventId, messageMap.message, eventDTO.senderId)
        ok "Request Completed"
    }  
    
}

