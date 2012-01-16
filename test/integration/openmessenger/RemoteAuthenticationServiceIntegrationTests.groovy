package openmessenger

import grails.test.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class RemoteAuthenticationServiceIntegrationTests extends GroovyTestCase {
	def remoteAuthenticationService
	
    protected void setUp() {
        super.setUp()
		/*if(SessionToken.count()) {
			SessionToken.list().each {
				it.delete(flush:true)
			}
		}*/
		
		MockUtils.mockLogging(RemoteAuthenticationService, true)
		//CH.config.grails.plugins.springsecurity.dao.reflectionSaltSourceProperty = ''
		def userone = new User(username:'userone',
			password:'password',
			firstname:'userone',
			lastname:'lastname',
			email:'email@email.com',
			enabled:true,
			accountExpired:false,
			accountLocked:false,
			passwordExpired:false
		)
		def usertwo = new User(username:'usertwo',
			password:'password',
			firstname:'usertwo',
			lastname:'lastname',
			email:'email@email.com',
			enabled:true,
			accountExpired:false,
			accountLocked:false,
			passwordExpired:false
		)
		
		userone.save()
		usertwo.save()				
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testAuthenticate() {
		// mock sessionToken
		def tokenOne = new SessionToken(username:'userone', token:'tokenasdf', issueDate:new Date())
		def tokenTwo = new SessionToken(username:'usertwo', token:'token2dd', issueDate:new Date())
		def tokenThree = new SessionToken(username:'userone', token:'token3sss', issueDate:(new Date()).previous())
				
		tokenOne.save(flush:true)
		tokenTwo.save(flush:true)
		tokenThree.save(flush:true)
		
		println "session token ${SessionToken.count()}"
		assertEquals 2, SessionToken.findAllByUsername('userone').size()
		def token = remoteAuthenticationService.authenticate('userone', 'password')
		assertNotNull(token)
		assertEquals 2, SessionToken.findAllByUsername('userone').size()		
    }
}
