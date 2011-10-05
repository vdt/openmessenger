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
		group = new GroupChat(codename:'g001', name:'group-chat', description:'mock group', occuredDate:new Date(), status:Status.NORMAL, type:Type.GROUP_CHAT, isSenderId:true)
		group.addToSubscribers(new Subscriber(msisdn:'12334567890', active:'Y'))
		group.addToSubscribers(new Subscriber(msisdn:'23435678901', active:'Y'))
		group.addToSubscribers(new Subscriber(msisdn:'34536789012', active:'Y'))
				
		group.validate()
		
		if(group.hasErrors()){
			group.errors.each {
				println it
			}
		}
		
		group.save(flush:true)		
		
		poll = new Poll(codename:'p002', name:'poll', description:'mock poll', occuredDate:new Date(), status:Status.NORMAL, type:Type.POLL, isSenderId:true)
		def sub = Subscriber.findByMsisdn('12334567890')
		poll.addToSubscribers(sub)
		poll.addToSubscribers(new Subscriber(msisdn:'123345678901', active:'Y'))
		poll.addToSubscribers(new Subscriber(msisdn:'234356789012', active:'Y'))
		poll.addToSubscribers(new Subscriber(msisdn:'345367890123', active:'Y'))
		poll.validate()
		
		if(poll.hasErrors()){
			poll.errors.each {
				println it
			}
		}
		poll.save(flush:true)
		
		secondGroup = new GroupChat(codename:'g003', name:'second-group-chat', description:'mock group2', occuredDate:new Date(), status:Status.NORMAL, type:Type.GROUP_CHAT, isSenderId:true)
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
				
		assertNotNull communicationService.getCommunicationTypeInstanceByCodeName('g001', Type.GROUP_CHAT, '12334567890')
		assertNotNull communicationService.getCommunicationTypeInstanceByCodeName('p002', Type.POLL, '123345678901')
    }
	
	void testGetCommunicationTypeInstanceByMsisdn() {
		assertNotNull communicationService.getCommunicationTypeInstanceByMsisdn(Type.GROUP_CHAT, '23435678901')
		assertNotNull communicationService.getCommunicationTypeInstanceByMsisdn(Type.POLL, '123345678901')
	}
	
	void testFailGetCommunicationTypeInstanceByMsisdn() {
		def p = communicationService.getCommunicationTypeInstanceByMsisdn(Type.POLL, '12334567890')
		assertEquals('poll', p.name)
		
		shouldFail(CommunicationException) { 
			communicationService.getCommunicationTypeInstanceByMsisdn(Type.GROUP_CHAT, '12334567890')
		}
	}
	
	void testGetGroupChatInstance(){
		def msisdn = '23435678901'
		def result = communicationService.getGroupChatInstance(msisdn)
		assertNotNull result
		if(result.size()==1) assertEquals(result.get(0).name, 'group-chat')
		else assertEquals 2, result.size()
	}
	
	void testGetPollInstance(){
		def msisdn = '12334567890'
		def result = communicationService.getPollInstance(msisdn)
		if(result.size()==1) assertEquals(result.get(0).name, 'poll')		
	}
	
	
	void testExtractGroupChatMsg() {
		def results = communicationService.extractMessage('g001', '12334567890', ' hello world');
		println results.eventId
		//assertEquals(2, results.eventId)
		assertEquals('hello world', results.message.content)
		assertEquals('12334567890', results.message.createBy)		
	}
	
	void testExtractNullCodeMsg(){
		def results = communicationService.extractMessage('null', '23435678901', ' hello world null');
		
		assertEquals('hello world null', results.message.content)
		assertEquals('23435678901', results.message.createBy)
		//assertEquals(2, results.eventId)
	}
	
	void testExtractPollMsg(){
		def results = communicationService.extractMessage('p002', '123345678901', ' 2');		
		//assertEquals('3', results.eventId)
		assertEquals('2', results.message.content)
		assertEquals('123345678901', results.message.createBy)		
	}
	
	void testFailExtractMsg(){
		shouldFail(CommunicationException) {
			communicationService.extractMessage('p002', '123345672348901', ' 2');
		}
	}
	
	void testHasAuthority(){
		assertEquals(true, communicationService.hasAuthority('admin', 'openpubyesroti!'))
		assertEquals(false, communicationService.hasAuthority('admin', 'openpubyesroti'))
	}
}
