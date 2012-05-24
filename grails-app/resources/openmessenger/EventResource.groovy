package openmessenger
import static org.grails.jaxrs.response.Responses.*
import grails.converters.JSON;

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.PathParam

import javax.ws.rs.core.MediaType;

import openmessenger.EventDTO

@Path('/api/event')
class EventResource {
    def eventService
    def communicationService
	def remoteAuthenticationService

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

    @GET
	@Path('/list/{username}/{token}')
	@Produces('application/json')
	JSON listEvents(@PathParam('username') String username,
								@PathParam('token') String token) {
		def events = [:]
		def enable = remoteAuthenticationService.hasSessionToken(username, token)
		if(enable) {
			def user = User.findByUsername(username)
			events = user.getEvents()
		} 
		return events as JSON 		
	}
								
	@GET
	@Path('/subscribers/{event_id}/{username}/{token}')
	@Produces(['application/xml','application/json'])
	JSON listSubscribers(@PathParam('event_id') Integer eventId,
					@PathParam('username') String username,
					@PathParam('token') String token) {
		def subscribers = [:]			
		def enable = remoteAuthenticationService.hasSessionToken(username, token)
		if(enable) {
			def event = Event.get(eventId)
			subscribers = event.subscribers
		} 
		return subscribers as JSON		
	}
					
	@POST
	@Path('/subscribe')
	@Consumes('application/json')
	@Produces('text/plain')
	Response subscribe(Map params) {
		def enable = remoteAuthenticationService.hasSessionToken(params.username, params.token)
		if(enable) {
			eventService.subscribeToEvent(params.eventId, params.msisdn)
			ok "Request Completed"
		} else {
			ok 'Error: Request not Completed'
		}
	}
	
	@GET
	@Path('/sendmessage/{event_id}/{username}/{token}/{message}')
	@Produces('text/plain')
	Response sendMessage(@PathParam('event_id') Long eventId,
							@PathParam('username') String username,
							@PathParam('token') String token,							
							@PathParam('message') String message) {
		def enable = remoteAuthenticationService.hasSessionToken(username, token)
		if(enable) {
			eventService.sendMessage(eventId, new Message(title:'test msg', content:message, createdDate:new Date()))
			ok "Request Completed"
		} else {
			ok 'Error: Request not Completed'
		}
	}
	
	@GET
    @Path('/sendPersonalMessage/{eventId}/{username}/{token}/{msisdn}/{message}')
    @Produces('text/plain')
    Response sendPersonalMessage(@PathParam('eventId') String eventId,
    								@PathParam('username') String username,
								    @PathParam('token') String password,
    								@PathParam('msisdn') String msisdn,
								  	@PathParam('message') String message) {
		def enable = remoteAuthenticationService.hasSessionToken(username, token)
		def user = User.findByUsername(username)
		def userEvent = UserEvent.get(user.id, eventId)
		if(enable && userEvent) {
			eventService.sendIndividualMessage(eventId, username, msisdn, new Message(title:'', content:message, createdDate:new Date()))
			ok "Request Completed"
		} else {
			ok 'Error: Request not Completed'
		}		  		
	}	
							
	@GET
	@Path('/messages/{event_id}/{username}/{token}')
	@Produces('application/json')
	JSON listMessages(@PathParam('event_id') Long eventId,
							@PathParam('username') String username,
							@PathParam('token') String token) {
		def enable = remoteAuthenticationService.hasSessionToken(username, token)
		if(enable) {
			def event = Event.get(eventId)
			def messages = []
			event.messages.each {
				messages.add([id:it.id, content:it.content, createBy:it.createBy, createdDate:it.createdDate])
			}
			
			messages as JSON
		} else {
			[:] as JSON
		}
	}
}

