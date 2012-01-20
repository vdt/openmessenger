package openmessenger

import org.grails.jaxrs.itest.IntegrationTestCase
import org.junit.Test

import grails.converters.JSON;
import grails.test.*
import openmessenger.Event.Status
import openmessenger.Event.Type
import static org.junit.Assert.*
import openmessenger.EventDTO
//import junit.framework.Test;


class EventResourceIntegrationTests extends IntegrationTestCase {
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
		//MockUtils.mockLogging(EventService, true)
		//CH.config.grails.plugins.springsecurity.dao.reflectionSaltSourceProperty = ''
		def user1 = User.findByUsername('boyone')
		if (!user1) {
			user1 = new User(username:'boyone',
				password:'password',
				firstname:'boyone',
				lastname:'lastname',
				email:'boyone@email.com',
				enabled:true,
				accountExpired:false,
				accountLocked:false,
				passwordExpired:false
			)
			user1.save()
		}
		def user2 = new User(username:'boytwo',
			password:'password',
			firstname:'boytwo',
			lastname:'lastname',
			email:'email@email.com',
			enabled:true,
			accountExpired:false,
			accountLocked:false,
			passwordExpired:false
		)
		user2.save()
		def user3 = User.findByUsername('default')
		if(!user3) {
			user3 =new User(username:'default',
				password:'password',
				firstname:'default',
				lastname:'lastname',
				email:'email@email.com',
				enabled:true,
				accountExpired:false,
				accountLocked:false,
				passwordExpired:false)
			user3.save()
		}		
		// mock sessionToken
		def sessionToken1 = new SessionToken(username:user1.username, token:'token', issueDate:new Date())
		def sessionToken2 = new SessionToken(username:'boytwo', token:'token2', issueDate:new Date())
		def sessionToken3 = new SessionToken(username:user1.username, token:'token3', issueDate:(new Date()).previous())
		def sessionToken4 = new SessionToken(username:'default', token:'token4', issueDate:(new Date()).previous())
				
		sessionToken1.save()
		sessionToken2.save()
		sessionToken3.save()
		sessionToken4.save()
		
		
		
		def headers = ['Content-Type':'application/json', 'Accept':'text/plain']
		
		/*
		 *  test /api/event/auth/$username/$password
		 */
		assertEquals 2, SessionToken.findAllByUsername(user1.username).size()
		sendRequest("/api/auth/${user1.username}/password", 'GET', headers)	
		assertEquals(200, response.status)
		def usertoken = response.contentAsString
		println "usertoken $usertoken"
		assertEquals 2, SessionToken.findAllByUsername(user1.username).size()
				
		/*
		*  test /api/event/ping
		*/
	   println "url xxx: /api/ping/${user1.username}/${usertoken}"
	   sendRequest("/api/ping/${user1.username}/${usertoken}", 'GET', headers)
	   assertEquals(200, response.status)
	   println response.contentAsString
	   assertEquals('ok', response.contentAsString)
	   
		/*
		 *  test /api/event/auth/$username/$password
		 */
		assertEquals 1, SessionToken.findAllByUsername('default').size()
		sendRequest('/api/auth/default/password', 'GET', headers)
		assertEquals(200, response.status)
		def defaultToken = response.contentAsString
		assertEquals 1, SessionToken.findAllByUsername('default').size()		
		assertEquals(SessionToken.findByUsername('default').token, defaultToken)		
		
		
		
		/*
		*  test /api/event/auth/$username/$password
		*  with unknown user
		*/
		sendRequest('/api/auth/error/password', 'GET', headers)
		println response.contentAsString
		assertEquals('error: not found', response.contentAsString)
	   
		/*
		 *  test /api/event/ping/$username/$token
		 */
		sendRequest("/api/ping/defaultx/$usertoken", 'GET', headers)
		assertEquals('error: not found', response.contentAsString)					
	}
	
	@Test
	void testListEvent() {
		def headers = ['Content-Type':'application/json', 'Accept':['application/json', 'text/plain']]
		def group = Event.findByName('group-chat3')
		if(!group) {
			group = new GroupChat(codename:'agkpbk3', name:'group-chat3', description:'mock group', occuredDate:new Date(), status:Status.NORMAL, type:Type.GROUP_CHAT, isSenderId:true)
			group.save()
		}
		def user = User.findByUsername('default3')
		if(!user) {
			user =new User(username:'default3',
				password:'password',
				firstname:'default3',
				lastname:'lastname',
				email:'email@email.com',
				enabled:true,
				accountExpired:false,
				accountLocked:false,
				passwordExpired:false)
			user.save()
		}
		//sendRequest('/api/event/auth/default/password', 'GET', headers)
		def token = remoteAuthenticationService.authenticate(user.username, 'password')		
		if(!UserEvent.get(user.id, group.id)) {
			UserEvent.create(user, group, true)
		}	
		
		sendRequest("/api/event/list/${user.username}/$token", 'GET', headers)
		assertEquals(200, response.status)
		println response.contentAsString
		
		
		sendRequest("/api/event/list/defaultx/$token", 'GET', headers)
		println response.contentAsString
		assertEquals('{}', response.contentAsString)		
	}
	
	@Test 
	void testListEventSubscribers() {
		
		def headers = ['Content-Type':'application/json', 'Accept':['application/json', 'text/plain']]
		def user = User.findByUsername('default2')
		if(!user) {
			user =new User(username:'default2',
				password:'password',
				firstname:'default2',
				lastname:'lastname',
				email:'email@email.com',
				enabled:true,
				accountExpired:false,
				accountLocked:false,
				passwordExpired:false)
			user.save()
		}
		def group = Event.findByName('group-chat2')
		if(!group) {
			group = new GroupChat(codename:'agkpbk2', name:'group-chat2', description:'mock group', occuredDate:new Date(), status:Status.NORMAL, type:Type.GROUP_CHAT, isSenderId:true)
			group.save()
		}
		// assigns user to event
		if(!UserEvent.get(user.id, group.id)) {
			UserEvent.create(user, group, true)
		}
		def token = remoteAuthenticationService.authenticate(user.username, 'password')
		def subscribers = group.subscribers?.size()
		if(!subscribers) {
			eventService.subscribeToEvent(group.id, '1234567890')
			eventService.subscribeToEvent(group.id, '2345678901')
			eventService.subscribeToEvent(group.id, '3456789012')			
		}
		/*
		 *  test /api/event/subscribers/$eventId/$username/$token
		 */
		sendRequest("/api/event/subscribers/${group.id}/${user.username}/$token", 'GET', headers)
		assertEquals(200, response.status)
		println response.contentAsString
	}
	
	//@Test
	void testSendMessage() {
		def headers = ['Content-Type':'application/json', 'Accept':['application/json', 'text/plain']]
		def user = User.findByUsername('default1')
		if(!user) {
			user =new User(username:'default1',
				password:'password',
				firstname:'default1',
				lastname:'lastname',
				email:'email@email.com',
				enabled:true,
				accountExpired:false,
				accountLocked:false,
				passwordExpired:false)
			user.save()
		}
		def group = Event.findByName('group-chat1')
		if(!group) {
			group = new GroupChat(codename:'agkpbk1', name:'group-chat1', description:'mock group', occuredDate:new Date(), status:Status.NORMAL, type:Type.GROUP_CHAT, isSenderId:true)
			group.save()
		}
		if(!UserEvent.get(user.id, group.id)) {
			UserEvent.create(user, group, true)
		}
		def token = remoteAuthenticationService.authenticate(user.username, 'password')
		def subscribers = group.subscribers?.size()
		if(!subscribers) {
			eventService.subscribeToEvent(group.id, '1234567890')
			eventService.subscribeToEvent(group.id, '2345678901')
			eventService.subscribeToEvent(group.id, '3456789012')
			subscribers = 3
		}
		
		//headers = ['Content-Type':'application/json', 'Accept':'text/plain']
		def rabbitSent=0
		eventService.metaClass.rabbitSend = {queue, msg -> rabbitSent++}
		def message = 'test msg'
		
		/*
		 *  test /api/event/sendmessage/$eventId/$username/$token/$message
		 */
		sendRequest("/api/event/sendmessage/${group.id}/${user.username}/$token/$message", 'GET', headers)
		assertEquals(200, response.status)
		assertEquals("Request Completed", response.contentAsString)
		assertEquals(subscribers, rabbitSent) // sent to 3 subscribers
		
		sendRequest("/api/event/sendmessage/${group.id}/${user.username}/$token/$message", 'GET', headers)
		assertEquals(200, response.status)
		sendRequest("/api/event/sendmessage/${group.id}/${user.username}/$token/$message", 'GET', headers)
		assertEquals(200, response.status)
		assertEquals(subscribers * 3, rabbitSent)
		
		/*
		 *  test /api/event/messages/$eventId/$username/$token
		 */
		sendRequest("/api/event/messages/${group.id}/${user.username}/$token", 'GET', headers)
		assertNotSame('{}', response.contentAsString)
		println response.contentAsString
	}

}
