package openmessenger

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response
import javax.ws.rs.PathParam


@Path('/api/event')
class EventResource {

    @GET
    @Path('/{id:[a-zA-Z][a-zA-Z_0-9]}/msisdn/{msisdn}/{content}/passphase/{username}/{password}')
    @Produces('text/plain')
    String getEventRepresentation(@PathParam('id') String id,
                                  @PathParam('msisdn') String msisdn,
								  @PathParam('content') String content, 
								  @PathParam('username') String username,
								  @PathParam('password') String password) 
	{
        'Event'
    }
    
}

