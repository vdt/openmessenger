package openmessenger

import java.util.Date;
import openmessenger.Event.Status
import openmessenger.Event.Type
import grails.test.*

class CommunicationServiceIntegrationTests extends GroovyTestCase {
	def communicationService
	def group
	def secondGroup
	def poll
	
    protected void setUp() {
		group = new GroupChat(codename:'g001', name:'group-chat', description:'mock group', occuredDate:new Date(), status:Status.NORMAL, type:Type.GROUP_CHAT)
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
		
		poll = new Poll(codename:'p002', name:'poll', description:'mock poll', occuredDate:new Date(), status:Status.NORMAL, type:Type.POLL)
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
		
		secondGroup = new GroupChat(codename:'g003', name:'second-group-chat', description:'mock group2', occuredDate:new Date(), status:Status.NORMAL, type:Type.GROUP_CHAT)
		secondGroup.addToSubscribers(sub)
		secondGroup.save(flush:true)
		
		MockUtils.mockLogging(CommunicationService, true)
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGetCommunicationTypeInstanceByCodeName() {
		assertEquals(3, group.subscribers.size())
				
		assertNotNull communicationService.getCommunicationTypeInstanceByCodeName('g001', Type.GROUP_CHAT, '1234567890')
		assertNotNull communicationService.getCommunicationTypeInstanceByCodeName('p002', Type.POLL, '12345678901')
    }
	
	void testGetCommunicationTypeInstanceByMsisdn() {
		assertNotNull communicationService.getCommunicationTypeInstanceByMsisdn(Type.GROUP_CHAT, '2345678901')
		assertNotNull communicationService.getCommunicationTypeInstanceByMsisdn(Type.POLL, '12345678901')
	}
	
	void testFailGetCommunicationTypeInstanceByMsisdn() {
		def p = communicationService.getCommunicationTypeInstanceByMsisdn(Type.POLL, '1234567890')
		assertEquals('poll', p.name)
		
		shouldFail(CommunicationException) { 
			communicationService.getCommunicationTypeInstanceByMsisdn(Type.GROUP_CHAT, '1234567890')
		}
	}
	
	void testGetGroupChatInstance(){
		def msisdn = '2345678901'
		def result = communicationService.getGroupChatInstance(msisdn)
		assertNotNull result
		if(result.size()==1) assertEquals(result.get(0).name, 'group-chat')
		else assertEquals 2, result.size()
	}
	
	void testGetPollInstance(){
		def msisdn = '1234567890'
		def result = communicationService.getPollInstance(msisdn)
		if(result.size()==1) assertEquals(result.get(0).name, 'poll')		
	}
	
	
	void testExtractGroupChatMsg() {
		def results = communicationService.extractMessage('g001', '1234567890', ' hello world');
		println results.eventId
		//assertEquals(2, results.eventId)
		assertEquals('hello world', results.message.content)
		assertEquals('1234567890', results.message.createBy)		
	}
	
	void testExtractNullCodeMsg(){
		def results = communicationService.extractMessage('null', '2345678901', ' hello world null');
		
		assertEquals('hello world null', results.message.content)
		assertEquals('2345678901', results.message.createBy)
		//assertEquals(2, results.eventId)
	}
	
	void testExtractPollMsg(){
		def results = communicationService.extractMessage('p002', '12345678901', ' 2');		
		//assertEquals('3', results.eventId)
		assertEquals('2', results.message.content)
		assertEquals('12345678901', results.message.createBy)		
	}
	
	void testFailExtractMsg(){
		shouldFail(CommunicationException) {
			communicationService.extractMessage('p002', '12345672348901', ' 2');
		}
	}
	
	void testHasAuthority(){
		assertEquals(true, communicationService.hasAuthority('admin', 'openpubyesroti!'))
		assertEquals(false, communicationService.hasAuthority('admin', 'openpubyesroti'))
	}
}
