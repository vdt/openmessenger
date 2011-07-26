package openmessenger

import grails.test.*
import org.apache.commons.lang.CharUtils
import org.apache.commons.lang.StringUtils;

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class ConsumerIntegrationTests extends GroovyTestCase {
	def consumerService
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSendMessage() {
		//text -> limited 189 characters
		def map = [msisdn:'66809737799', content:'Call me RabbitMQ Dude ทดสอบไทย ព្រះរាជាណាចក្រកម្ពុជា  tiếng Việt, Việt ngữ']
		//def map = [msisdn:'66800892412', content:'ทดสอบไทย']
		
		/*def result = consumerService.sendMessage(map)
		assertTrue result.toString().contains('ID:')*/
		
		assertEquals 'http://api.clickatell.com', CH.config.sms.gateway.uri
		//consumerService.handleMessage([msisdn:'66860546930', content:'Hello from OpenMessenger!'])		
		//consumerService.handleMessage([msisdn:'66897777367', content:'Hello from OpenMessenger!'])			
		
		
    }
}
