package openmessenger
import static org.grails.jaxrs.response.Responses.*
import grails.converters.JSON;
import java.util.Map
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
import org.grails.jaxrs.provider.JSONReader

import javax.ws.rs.core.MediaType;

import openmessenger.User;

@Path('/api')
class AuthResource {
	def remoteAuthenticationService
	
	@GET
	@Path('/auth/{username}/{password}') 
	@Produces('text/plain')
	Response authenticateUser(@PathParam('username') String username,
								@PathParam('password') String password) {
		def token = remoteAuthenticationService.authenticate(username, password)
		if(token) {
			ok token
		} else {			
			ok 'Error: not found'
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
			ok 'Error: not found'
		}
	}
}