package openmessenger

import grails.test.*

class OpenMessengerIntegrationTests extends GroovyTestCase {
	def eventService
	def consumerService
		
    protected void setUp() {		
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSentMessegeToRabbitMqPullQueueAndSendSms() {	
		// create Event
		Event event = new Event(name: 'The Championships',
			description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
			occuredDate: new Date(), status: 'NORMAL')
		
        assertTrue event.validate()		
        assertNotNull event.save()		
        assertNotNull event.id		
		
		
		// create Subscribers and add to event
		List subscribers = [	new Subscriber(msisdn: '66890242989', active: 'Y'),
			new Subscriber(msisdn: '66800892412', active: 'Y')]
		event.subscribers = subscribers
		
		assertNotNull event.save()		
		assertEquals 2, event.subscribers.size()	
	
		
		// create Messege and send to rabbitMq
		def message = new Message(title:"new messege", content:'Call me RabbitMQ Dude ทดสอบไทย ព្រះរាជាណាចក្រកម្ពុជា  tiếng Việt, Việt ngữ', createdDate:new Date())
		
		eventService.sendMessage(event.id, message)
		
		assertEquals 1, event.messages.size()
		
		
		// waiting for consumerService process (60sec)
		Thread.sleep 240000
	}
}
