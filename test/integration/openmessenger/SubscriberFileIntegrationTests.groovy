package openmessenger

import grails.test.*

class SubscriberFileIntegrationTests extends GroovyTestCase {
	def subscriberFileService
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testParseCsv() {
		File file = new File("data/subscribers-test.csv")
		List subscribers = subscriberFileService.parseCsv(file)
		
		assertEquals "66896959009", subscribers.get(1)
		assertEquals 4, subscribers.size()
    }
}
