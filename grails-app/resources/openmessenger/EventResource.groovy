package openmessenger
import static org.grails.jaxrs.response.Responses.*
import grails.converters.JSON;

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
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
	@Path('/auth/{username}/{password}') 
	@Produces('text/plain')
	Response authenticateUser(@PathParam('username') String username,
								@PathParam('password') String password) {
		def token = remoteAuthenticationService.authenticate(username, password)
		if(token) {
			ok token
		} else {			
			ok 'error: not found'
		}
	}
    
	@GET
	@Path('/ping/{username}/{token}')
	@Produces('text/plain')
	Response ping(@PathParam('username') String username,
								@PathParam('token') String token) {
		def enable = remoteAuthenticationService.hasSessionToken(username, token)	
		if(enable) {
			ok 'ok'
		} else {
			ok 'error: not found'
		}
	}
								
	@GET
	@Path('/list/{username}/{token}')
	@Produces(['application/xml','application/json'])
	def listEvents(@PathParam('username') String username,
								@PathParam('token') String token) {
		def enable = remoteAuthenticationService.hasSessionToken(username, token)
		if(enable) {
			def user = User.findByUsername(username)
			user.getEvents()
		} else {
			'error: not found'
		}
	}
								
	@GET
	@Path('/subscribers/{username}/{token}/{event_id}')
	@Produces(['application/xml','application/json'])
	def listSubscribers(@PathParam('username') String username,
					@PathParam('token') String token,
					@PathParam('event_id') Integer eventId) {
		def enable = remoteAuthenticationService.hasSessionToken(username, token)
		if(enable) {
			def event = Event.get(eventId)
			event.subscribers
		} else {
			'error: not found'
		}
	}
			
	@GET
	@Path('/sendmessage/{username}/{token}/{event_id}/{message}')
	@Produces('text/plain')
	Response sendMessage(@PathParam('username') String username,
							@PathParam('token') String token,
							@PathParam('event_id') Long eventId,
							@PathParam('message') String message) {
		def enable = remoteAuthenticationService.hasSessionToken(username, token);
		if(enable) {
			eventService.sendMessage(eventId, new Message(title:'test msg', content:message, createdDate:new Date()))
			ok "Request Completed"
		} else {
			ok 'Error: Request not Completed'
		}
	}
							
	@GET
	@Path('/messages/{username}/{token}/{event_id}')
	@Produces('application/json')
	JSON listMessages(@PathParam('username') String username,
							@PathParam('token') String token,
							@PathParam('event_id') Long eventId) {
		def enable = remoteAuthenticationService.hasSessionToken(username, token)
		if(enable) {
			def event = Event.get(eventId)
			def messages = []
			event.messages.each {
				messages.add([id:it.id, content:it.content, createBy:it.createBy, createdDate:it.createdDate])
			}
			
			messages as JSON
		} else {
			'error: not found'
		}
	}
}

