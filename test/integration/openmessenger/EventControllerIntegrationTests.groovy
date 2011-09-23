package openmessenger

import grails.test.*
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;

class EventControllerIntegrationTests extends grails.test.ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testListAllEvents(){
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                new GrailsUser("admin",
                        "admin",
                        true,
                        true,
                        true,
                        true,
                        [ new GrantedAuthorityImpl('ROLE_ADMIN')],
                        null),
                "admin"))
                
        def events =  controller.listAllEvents()
        assertNotNull events
		
        //assertEquals "listAllEvents", controller.renderArgs.view
    }
}
