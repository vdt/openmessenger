package openmessenger

import grails.test.*
import org.apache.commons.lang.CharUtils
import org.apache.commons.lang.StringUtils;

class ConsumerServiceTests extends GrailsUnitTestCase {
	def consumerService
	
    protected void setUp() {
		consumerService = new ConsumerService()
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testHandleMessage() {
		def maps = [[uri:'http1', to:'66890242989', text:'Call me RabbitMQ dude'], 
			[uri:'http2', to:'66890242989', text:'Call me RabbitMQ dude'], 
			[uri:'http3', to:'66890242989', text:'Call me RabbitMQ dude']]
		def counter = 0
		consumerService.metaClass.withHttp = {Map map, Closure closure -> counter++}

		maps.each { 
			consumerService.handleMessage(it)
		}
		
		assertEquals 3, counter
    }
	
	void testConvertToUnicode() {		
		String msg = 'ไทย'
		String result = consumerService.convertToUnicode(msg)
		assertEquals '0e440e170e22', result
	}
}
