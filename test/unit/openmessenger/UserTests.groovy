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
		
		user = new User(username: "roof,,,,", password: "pass***", role: "user")
		assertFalse user.validate()
		assertEquals "matches", user.errors["username"]     
				
		user = new User(username: "roofimon", password: "@S3cr3T@S3cr3T@S3cr3T", role: "user")                        
		assertFalse user.validate()  
		assertEquals "size", user.errors["password"]   

		user = new User(username: "roofimon", password: "S3cr3t", role: "user")                        
		assertFalse user.validate()  
		assertEquals "matches", user.errors["password"]
		
		user = new User(username: "roofimon", password: "s3creT#", role: "not admin")
		assertFalse user.validate()
		assertEquals "inList", user.errors["role"]
		
		user = new User(username: "roofimon", password: "s3creT#", role: "admin")                        
		assertTrue user.validate()		
    } 

	
/*
Must be at least 10 characters
Must contain at least one one lower case letter, one upper case letter, one digit and one special character
Valid special characters are -   @#$%^&+=
*/
}
