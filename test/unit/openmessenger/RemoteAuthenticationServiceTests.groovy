package openmessenger

import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import grails.plugins.springsecurity.SpringSecurityService;
import grails.test.*

class RemoteAuthenticationServiceTests extends GrailsUnitTestCase {
	def remoteAuthenticationService
	def springSecurityService
	
    protected void setUp() {
        super.setUp()
		mockConfig ('''
		grails.plugins.springsecurity.dao.reflectionSaltSourceProperty="asdf"		
		''')
		MockUtils.mockLogging(RemoteAuthenticationService, true)
		
		User.metaClass.isDirty = { password -> false }
		def springSecurityService = mockFor(SpringSecurityService)
		springSecurityService.demand.encodePassword(1..1) { password, salt -> password + salt }
				
		remoteAuthenticationService = new RemoteAuthenticationService()
		remoteAuthenticationService.springSecurityService = springSecurityService.createMock()
		
		def user1 = new User(username:'boyone',
							password:'passwordasdf',
							firstname:'password',
							lastname:'lastname',
							email:'email@email.com',
							enabled:true,
							accountExpired:false,
							accountLocked:false,
							passwordExpired:false
			)
		def user2 = new User(username:'boytwo',
			password:'passwordasdf',
			firstname:'password',
			lastname:'lastname',
			email:'email@email.com',
			enabled:true,
			accountExpired:false,
			accountLocked:false,
			passwordExpired:false
			)
		
		mockDomain(User, [user1, user2])
		
		user1.save()
		user2.save()
		
		// mock sessionToken
		def sessionToken1 = new SessionToken(username:'boyone', token:'token', issueDate:new Date())		
		def sessionToken2 = new SessionToken(username:'boytwo', token:'token2', issueDate:new Date())
		def sessionToken3 = new SessionToken(username:'boyone', token:'token3', issueDate:(new Date()).previous())
		def sessionToken4 = new SessionToken(username:'default', token:'token4', issueDate:(new Date()).previous())
		mockDomain(SessionToken, [sessionToken1, sessionToken2, sessionToken3, sessionToken4])
		
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
		println SessionToken.count()
		
    }
	
	void testHasSessionToken() {
		def sessionToken = SessionToken.findByUsername('boytwo')
		def token = remoteAuthenticationService.hasSessionToken(sessionToken.username, sessionToken.token)
		
		assertEquals(true, token)
		
		token = remoteAuthenticationService.hasSessionToken('unknowuser', sessionToken.token)
		assertEquals(false, token)
	}
	
	void testCreateNewToken() {
		def set = []
		10.times {
			set.add(remoteAuthenticationService.createNewToken('a'))
		}
		assertEquals 10, set.size()
	}
	
	void testGetSessionToken(){
		def sessionToken = SessionToken.findByUsername('boytwo')
		def token = remoteAuthenticationService.getSessionToken(sessionToken.username, sessionToken.token)
		assertNotNull(token)
		
		token = remoteAuthenticationService.getSessionToken("asdf", sessionToken.token)
		assertNull(token)
	}
	
	void testIsEnable() {
		def sessionToken = SessionToken.findByUsername('boytwo')
		def enable = remoteAuthenticationService.isEnable(sessionToken)
		assertEquals true, enable
		
		sessionToken = SessionToken.findByUsername('default')
		enable = remoteAuthenticationService.isEnable(sessionToken)
		assertEquals false, enable
		
		enable = remoteAuthenticationService.isEnable(null)
		assertEquals false, enable
	}
	
	void testRemoveExpiredSessionToken() {
		assertEquals 2, SessionToken.findAllByUsername('boyone').size()
		remoteAuthenticationService.removeExpiredSessionToken('boyone')
		assertEquals 1, SessionToken.findAllByUsername('boyone').size()
	}
	
	void testGenerateTokenData() {
		def set = []
		100.times {
			set.add(remoteAuthenticationService.generateTokenData())
		}
		set = set as Set
		assertEquals 100, set.size()
	}
}
