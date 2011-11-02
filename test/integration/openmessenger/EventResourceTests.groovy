package openmessenger

import org.grails.jaxrs.itest.IntegrationTestCase
import org.junit.Test
import grails.test.*
import openmessenger.Event.Status
import openmessenger.Event.Type
import static org.junit.Assert.*
import openmessenger.EventDTO


class EventResourceTests extends IntegrationTestCase {
	def communicationService
	def eventService

	
	@Test
    void testExtractMessageAndSend() {
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
		
	    MockUtils.mockLogging(CommunicationService, true)
		MockUtils.mockLogging(EventService, true)
		
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

}
