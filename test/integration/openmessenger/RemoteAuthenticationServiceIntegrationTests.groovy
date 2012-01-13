package openmessenger

import grails.test.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class RemoteAuthenticationServiceIntegrationTests extends GroovyTestCase {
	def remoteAuthenticationService
	
    protected void setUp() {
        super.setUp()
		
		MockUtils.mockLogging(RemoteAuthenticationService, true)
		//CH.config.grails.plugins.springsecurity.dao.reflectionSaltSourceProperty = ''
		def user1 = new User(username:'boyone',
			password:'password',
			firstname:'password',
			lastname:'lastname',
			email:'email@email.com',
			enabled:true,
			accountExpired:false,
			accountLocked:false,
			passwordExpired:false
		)
		def user2 = new User(username:'boytwo',
			password:'password',
			firstname:'password',
			lastname:'lastname',
			email:'email@email.com',
			enabled:true,
			accountExpired:false,
			accountLocked:false,
			passwordExpired:false
		)
		
		user1.save()
		user2.save()
		
		// mock sessionToken
		def sessionToken1 = new SessionToken(username:'boyone', token:'token', issueDate:new Date())
		def sessionToken2 = new SessionToken(username:'boytwo', token:'token2', issueDate:new Date())
		def sessionToken3 = new SessionToken(username:'boyone', token:'token3', issueDate:(new Date()).previous())
		def sessionToken4 = new SessionToken(username:'default', token:'token4', issueDate:(new Date()).previous())
				
		sessionToken1.save()
		sessionToken2.save()
		sessionToken3.save()
		sessionToken4.save()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testAuthenticate() {
		assertEquals 2, SessionToken.findAllByUsername('boyone').size()
		def token = remoteAuthenticationService.authenticate('boyone', 'password')
		assertNotNull(token)
		assertEquals 2, SessionToken.findAllByUsername('boyone').size()		
    }
}
