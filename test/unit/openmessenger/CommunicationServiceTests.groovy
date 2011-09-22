package openmessenger

import grails.test.*
import openmessenger.Event.Type

class CommunicationServiceTests extends GrailsUnitTestCase {
	def communicationService
    protected void setUp() {
		communicationService = new CommunicationService()
		MockUtils.mockLogging(CommunicationService, true)
		mockConfig ('''
		grails.plugins.springsecurity.dao.reflectionSaltSourceProperty=""
		''')
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
		
	void testGetType(){
		assertEquals(Type.GROUP_CHAT, communicationService.getType('g123'))
		assertEquals(Type.POLL, communicationService.getType('p123'))
		assertEquals('undefined', communicationService.getType(null))
		assertEquals('undefined', communicationService.getType(''))
	}
}
