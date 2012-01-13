package openmessenger

import org.grails.jaxrs.itest.IntegrationTestCase
import org.junit.Test
import grails.test.*
import openmessenger.Event.Status
import openmessenger.Event.Type
import static org.junit.Assert.*
import openmessenger.EventDTO
//import junit.framework.Test;


class EventResourceTests extends IntegrationTestCase {
	def communicationService
	def eventService
	def remoteAuthenticationService
	
	@Test
    void testExtractMessageAndSend() {
		
		MockUtils.mockLogging(CommunicationService, true)
		MockUtils.mockLogging(EventService, true)
		
		
		def group = new GroupChat(codename:'agkpbk', name:'group-chat', description:'mock group', occuredDate:new Date(), status:Status.NORMAL, type:Type.GROUP_CHAT, isSenderId:true)
		/*group.addToSubscribers(new Subscriber(msisdn:'1234567890', active:'Y'))
		group.addToSubscribers(new Subscriber(msisdn:'2345678901', active:'Y'))
		group.addToSubscribers(new Subscriber(msisdn:'3456789012', active:'Y'))*/
				
		group.validate()

		if(group.hasErrors()){
			group.errors.each {
				println it
			}
		}
		
		group.save(flush:true)
		
		eventService.subscribeToEvent(group.id, '1234567890')
		eventService.subscribeToEvent(group.id, '2345678901')
		eventService.subscribeToEvent(group.id, '3456789012')
		
		def poll = new Poll(codename:'p2', name:'poll', description:'mock poll', occuredDate:new Date(), status:Status.NORMAL, type:Type.POLL, isSenderId:true)
		def sub = Subscriber.findByMsisdn('1234567890')
		
		/*poll.addToSubscribers(sub)
		poll.addToSubscribers(new Subscriber(msisdn:'12345678901', active:'Y'))
		poll.addToSubscribers(new Subscriber(msisdn:'23456789012', active:'Y'))
		poll.addToSubscribers(new Subscriber(msisdn:'34567890123', active:'Y'))*/		
		
		poll.validate()
		
		if(poll.hasErrors()){
			poll.errors.each {
				println it
			}
		}
		poll.save(flush:true)
		eventService.subscribeToEvent(poll.id, sub.msisdn)
		eventService.subscribeToEvent(poll.id, '12345678901')
		eventService.subscribeToEvent(poll.id, '23456789012')
		eventService.subscribeToEvent(poll.id, '34567890123')		
	    
		
		def rabbitSent=0
		eventService.metaClass.rabbitSend = {queue, msg -> rabbitSent++}

		def secondGroup = new GroupChat(codename:'gabc', name:'second-group-chat', description:'mock group2', occuredDate:new Date(), status:Status.NORMAL, type:Type.GROUP_CHAT, isSenderId:true)
		//secondGroup.addToSubscribers(sub)
		secondGroup.save(flush:true)
		eventService.subscribeToEvent(secondGroup.id, sub.msisdn)

        def headers = ['Content-Type':'application/json', 'Accept':'text/plain']
        def content = '{"class":"openmessenger.EventDTO","codename":"agkpbk","content":"Hill", "msisdn":"1234567890", "senderId":"6281287925981", "username":"roofimon", "password":"password"}'
        def expected = 'Request Completed'

        
        def target = GroupChat.findByCodename('agkpbk')
        assertNotNull target

        def results = communicationService.extractMessage('agkpbk', '1234567890', ' helloworld');
		//println results.eventId
		//assertEquals(2, results.eventId)
		assertEquals('helloworld', results.message.content)
		assertEquals('1234567890', results.message.createBy)
		
        sendRequest('/api/event/agkpbk/msisdn/1234567890/6281287925981/hello/passphase/roofimon/passw0rd','GET', headers)
        
        assertEquals(200, response.status)
        assertEquals("${expected}".toString(), response.contentAsString)
		assertEquals(2, rabbitSent)
		
		

        sendRequest('/api/event','POST', headers, content.bytes)
        
        assertEquals(200, response.status)
        assertEquals("${expected}".toString(), response.contentAsString)	
		
		
		
		//2345678901
		rabbitSent = 0
		println 'test null codename started .....'
		sendRequest('/api/event/null/msisdn/23456789012/6281287925981/hello/passphase/roofimon/passw0rd','GET', headers)		
		assertEquals(200, response.status)
		assertEquals("${expected}".toString(), response.contentAsString)
		assertEquals(3, rabbitSent)
    }
	
	@Test
	void testAuthenticate() {		
		
		MockUtils.mockLogging(RemoteAuthenticationService, true)
		MockUtils.mockLogging(EventService, true)
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
		
		def user3 = new User(username:'default',
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
		user3.save()
		
		// mock sessionToken
		def sessionToken1 = new SessionToken(username:'boyone', token:'token', issueDate:new Date())
		def sessionToken2 = new SessionToken(username:'boytwo', token:'token2', issueDate:new Date())
		def sessionToken3 = new SessionToken(username:'boyone', token:'token3', issueDate:(new Date()).previous())
		def sessionToken4 = new SessionToken(username:'default', token:'token4', issueDate:(new Date()).previous())
				
		sessionToken1.save()
		sessionToken2.save()
		sessionToken3.save()
		sessionToken4.save()
		
		def headers = ['Content-Type':'application/json', 'Accept':'text/plain']
		
		assertEquals 2, SessionToken.findAllByUsername('boyone').size()		
		sendRequest('/api/event/auth/boyone/password', 'GET', headers)	
		assertEquals(200, response.status)
		assertEquals 2, SessionToken.findAllByUsername('boyone').size()
		
		
		assertEquals 1, SessionToken.findAllByUsername('default').size()
		sendRequest('/api/event/auth/default/password', 'GET', headers)
		assertEquals(200, response.status)
		assertEquals 1, SessionToken.findAllByUsername('default').size()		
		def token = response.contentAsString
		println token
		assertNotNull(token)
		assertEquals(SessionToken.findByUsername('default').token, token)		
		
		sendRequest('/api/event/auth/error/password', 'GET', headers)
		println response.contentAsString
		assertEquals('error: not found', response.contentAsString)
		
		sendRequest("/api/event/ping/default/$token", 'GET', headers)
		assertEquals(200, response.status)
		println response.contentAsString
		assertEquals('ok', response.contentAsString)
		
		sendRequest("/api/event/ping/defaultx/$token", 'GET', headers)
		assertEquals('error: not found', response.contentAsString)
		
		
		def group = new GroupChat(codename:'agkpbk1', name:'group-chat1', description:'mock group', occuredDate:new Date(), status:Status.NORMAL, type:Type.GROUP_CHAT, isSenderId:true)
		group.save()
		
		UserEvent.create(user3, group, true)
		headers = ['Content-Type':'application/json', 'Accept':['application/json', 'text/plain']]
		sendRequest("/api/event/list/default/$token", 'GET', headers)
		assertEquals(200, response.status)
		println response.contentAsString
		
		sendRequest("/api/event/list/defaultx/$token", 'GET', headers)
		assertEquals('error: not found', response.contentAsString)
		
		eventService.subscribeToEvent(group.id, '1234567890')
		eventService.subscribeToEvent(group.id, '2345678901')
		eventService.subscribeToEvent(group.id, '3456789012')
		
		sendRequest("/api/event/subscribers/default/$token/${group.id}", 'GET', headers)
		assertEquals(200, response.status)
		println response.contentAsString
		
		
		//headers = ['Content-Type':'application/json', 'Accept':'text/plain']
		def rabbitSent=0
		eventService.metaClass.rabbitSend = {queue, msg -> rabbitSent++}		
		def message = 'test msg'
		sendRequest("/api/event/sendmessage/default/$token/${group.id}/$message", 'GET', headers)
		assertEquals(200, response.status)
		assertEquals("Request Completed", response.contentAsString)
		assertEquals(3, rabbitSent) // sent to 3 subscribers
		
		sendRequest("/api/event/sendmessage/default/$token/${group.id}/$message", 'GET', headers)
		assertEquals(200, response.status)
		sendRequest("/api/event/sendmessage/default/$token/${group.id}/$message", 'GET', headers)
		assertEquals(200, response.status)
		assertEquals(9, rabbitSent)
		
		
		sendRequest("/api/event/messages/default/$token/${group.id}", 'GET', headers)
		assertEquals(200, response.status)
		println response.contentAsString
	}

}
