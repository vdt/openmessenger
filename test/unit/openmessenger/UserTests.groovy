package openmessenger

import grails.test.*

class UserTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testUserConstraints() {
		def user = new User()   
		mockForConstraintsTests(User, [user])
		assertFalse user.validate()	
    } 

	
/*
Must be at least 10 characters
Must contain at least one one lower case letter, one upper case letter, one digit and one special character
Valid special characters are -   @#$%^&+=
*/
}
