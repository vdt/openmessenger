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
		def group = new GroupChat(codename:'G1', name:'group-chat', description:'mock group', occuredDate:new Date(), status:Status.NORMAL, type:Type.GROUP_CHAT)
		group.addToSubscribers(new Subscriber(msisdn:'1234567890', active:'Y'))
		group.addToSubscribers(new Subscriber(msisdn:'2345678901', active:'Y'))
		group.addToSubscribers(new Subscriber(msisdn:'3456789012', active:'Y'))
				
		group.validate()

		if(group.hasErrors()){
			group.errors.each {
				println it
			}
		}
		
		group.save(flush:true)		
		
		def poll = new Poll(codename:'p2', name:'poll', description:'mock poll', occuredDate:new Date(), status:Status.NORMAL, type:Type.POLL)
		def sub = Subscriber.findByMsisdn('1234567890')
		poll.addToSubscribers(sub)
		poll.addToSubscribers(new Subscriber(msisdn:'12345678901', active:'Y'))
		poll.addToSubscribers(new Subscriber(msisdn:'23456789012', active:'Y'))
		poll.addToSubscribers(new Subscriber(msisdn:'34567890123', active:'Y'))
		poll.validate()
		
		if(poll.hasErrors()){
			poll.errors.each {
				println it
			}
		}
		poll.save(flush:true)
	    MockUtils.mockLogging(CommunicationService, true)


		def secondGroup = new GroupChat(codename:'g3', name:'second-group-chat', description:'mock group2', occuredDate:new Date(), status:Status.NORMAL, type:Type.GROUP_CHAT)
		secondGroup.addToSubscribers(sub)
		secondGroup.save(flush:true)

        def headers = ['Content-Type':'application/json', 'Accept':'text/plain']
        def content = '{"class":"openmessenger.EventDTO","codename":"g1","content":"Hill", "msisdn":"66809737799", "username":"roofimon", "password":"password"}'
        def expected = 'Request Completed'

        
        def target = GroupChat.findByCodename('g1')
        assertNotNull target

        def results = communicationService.extractMessage('g1', '1234567890', ' helloworld');
		//println results.eventId
		//assertEquals(2, results.eventId)
		assertEquals('helloworld', results.message.content)
		assertEquals('1234567890', results.message.createBy)

        sendRequest('/api/event/g1/msisdn/1234567890/hello/passphase/roofimon/passw0rd','GET', headers)
        
        assertEquals(200, response.status)
        assertEquals("${expected}".toString(), response.contentAsString)

        sendRequest('/api/event','POST', headers, content.bytes)
        
        assertEquals(200, response.status)
        assertEquals("${expected}".toString(), response.contentAsString)
    }

}
