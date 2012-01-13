package openmessenger

import grails.test.*

class SessionTokenTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidate() {
		def sessionToken = new SessionToken(username:'boyone', token:'token')
		mockDomain(SessionToken, [sessionToken])
		assertTrue(sessionToken.validate())		
    }
	
	void testSave(){ 
		def sessionToken = new SessionToken(username:'boyone', token:'token')
		mockDomain(SessionToken, [sessionToken])
		assertTrue(sessionToken.validate())
		sessionToken.save()
		
		assertEquals(1, SessionToken.count())
		assertEquals(1, sessionToken.id)
	}
	
	void testRemove(){
		def sessionToken1 = new SessionToken(username:'boyone', token:'token')
		def sessionToken2 = new SessionToken(username:'boytwo', token:'token2')
		mockDomain(SessionToken, [sessionToken1, sessionToken2])
		assertTrue(sessionToken1.validate())
		sessionToken1.save()
		sessionToken2.save()
		
		assertEquals(2, SessionToken.count())
		assertEquals(1, sessionToken1.id)
		
		sessionToken1.delete()
		assertEquals(1, SessionToken.count())
	}
	
	void testFindByUserName() {
		def sessionToken1 = new SessionToken(username:'boyone', token:'token')
		def sessionToken2 = new SessionToken(username:'boytwo', token:'token2')
		mockDomain(SessionToken, [sessionToken1, sessionToken2])
		
		sessionToken1.save()
		sessionToken2.save()
		
		def sessionTokens = SessionToken.findAllByUsername('boyone')
		assertEquals(1, sessionTokens.size())
		
		sessionTokens = SessionToken.findAllByUsername('boyonex')
		assertEquals(0, sessionTokens.size())
	}
	
	void testFindByUserNameAndToken() {
		def sessionToken1 = new SessionToken(username:'boyone', token:'token')
		def sessionToken2 = new SessionToken(username:'boytwo', token:'token2')
		mockDomain(SessionToken, [sessionToken1, sessionToken2])
		
		sessionToken1.save()
		sessionToken2.save()
		
		def sessionTokens = SessionToken.findAllByUsernameAndToken('boyone', 'token')
		assertEquals(1, sessionTokens.size())
		
		sessionTokens = SessionToken.findAllByUsernameAndToken('boyonex', 'token')
		assertEquals(0, sessionTokens.size())
	}
}
