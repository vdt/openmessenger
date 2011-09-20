package openmessenger

import grails.test.*
import openmessenger.Event.Type

class TwoWayServiceTests extends GrailsUnitTestCase {
	def twoWayService
    protected void setUp() {
		twoWayService = new TwoWayService()
		MockUtils.mockLogging(TwoWayService, true)
		mockConfig ('''
		grails.plugins.springsecurity.dao.reflectionSaltSourceProperty=""
		''')
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testExtractGroupChatMsg() {
		def msg = [msisdn:'66890242989', msg:'#g01 hello world']
		def results = twoWayService.extractMsg(msg);
		
		assertEquals('g01', results.code)
		assertEquals('hello world', results.msg)
		assertEquals('66890242989', results.msisdn)
		
		msg = [msisdn:'66890242989', msg:'#G01 hello world']
		results = twoWayService.extractMsg(msg);
		
		assertEquals('g01', results.code)
		assertEquals('hello world', results.msg)
		assertEquals('66890242989', results.msisdn)
		assertEquals(Type.GROUP_CHAT, results.type)
    }
	
	void testExtractNoCodeMsg(){
		def msg = [msisdn:'66890242989', msg:' hello world']
		def results = twoWayService.extractMsg(msg);
		
		assertEquals(null, results.code)
		assertEquals('hello world', results.msg)
		assertEquals('66890242989', results.msisdn)
		assertEquals(Type.GROUP_CHAT, results.type)
	}
	
	void testExtractPollMsg(){
		def msg = [msisdn:'66890242989', msg:'#p0001 2']
		def results = twoWayService.extractMsg(msg);
		
		assertEquals('p0001', results.code)
		assertEquals('2', results.msg)
		assertEquals('66890242989', results.msisdn)
		assertEquals(Type.POLL, results.type)
	}
}
