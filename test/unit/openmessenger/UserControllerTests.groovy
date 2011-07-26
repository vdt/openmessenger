package openmessenger

import grails.test.*

class UserControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testAuthentication() {
		def jdoe = new User(username:"roofimon", password:"s3creT#") 
		mockDomain(User, [jdoe]) 
		controller.params.username = "roofimon" 
		controller.params.password = "s3creT#" 
		controller.authenticate()
		assertNotNull controller.session.user 
		assertEquals "roofimon", controller.session.user.username 
		controller.params.password = "foo" 
		controller.authenticate() 
		assertTrue controller.flash.message.startsWith("Sorry, roofimon")	
    }

	void testLogout(){
		def jdoe = new User(username:"roofimon", password:"s3creT#") 
		mockDomain(User, [jdoe])
		controller.session.user = jdoe
		controller.logout()
		assertNull controller.session.user
	}
}
