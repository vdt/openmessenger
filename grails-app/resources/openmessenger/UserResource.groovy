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

@Path('/api/user')
class UserResource {
	
	@PUT
	@Consumes('application/json')
    @Produces('application/json')
	JSON updateUser(Map params) {
		def userInstance = []
		if(params.id) {
			userInstance = User.get(params.id)
			println 'start'		
			params.each {	key, value -> 				
				if(userInstance.hasProperty(key) && key != 'class' && key != 'id') {
					userInstance.setProperty(key, value)
				}
			}
			userInstance.save()			
		}
		return userInstance as JSON
	}
	
	@POST
	@Consumes('application/json')
	@Produces('application/json')
	JSON create(Map params) {
		def user = new User(params)
		user.save()
		return user as JSON						
	}
}