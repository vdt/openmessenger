package openmessenger

import grails.test.*

class UserTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
		def user1 = new User(username:'boyone',
			password:'passworda',
			firstname:'boyone',
			lastname:'lastname',
			email:'email@email.com',
			enabled:true,
			accountExpired:false,
			accountLocked:false,
			passwordExpired:false
		)
		mockDomain(User, [user1])
		//user1.save()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testUserConstraints() {
		def user = new User()   
		mockForConstraintsTests(User, [user])
		assertFalse user.validate()	
    } 
	
	void testUserProperties(){
		User.metaClass.getAuthorities = {}
		User.metaClass.getEvents = {}
		
		def user = User.get(1)			
		assertTrue user.properties.containsKey('password')
		assertFalse user.properties.containsKey('passwordx')
	}

	
/*
Must be at least 10 characters
Must contain at least one one lower case letter, one upper case letter, one digit and one special character
Valid special characters are -   @#$%^&+=
*/
}
