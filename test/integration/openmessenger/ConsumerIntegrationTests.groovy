package openmessenger

import grails.test.*
import org.apache.commons.lang.CharUtils
import org.apache.commons.lang.StringUtils;

class ConsumerIntegrationTests extends GroovyTestCase {
	def consumerService
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSendMessage() {
		def map = [to:'66890242989', text:'Call me RabbitMQ dude ทดสอบไทย']
		def result = consumerService.handleMessage(map)
		
		assertNotNull result.toString().contains('ID')
    }
}
