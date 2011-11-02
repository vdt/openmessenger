package openmessenger

import grails.test.*

class GatewayTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidate() {
		def gateway = new Gateway(prefix:'00', name:'inter_clickatell', queueName:'openmessenger', createdBy:'admin', messageSize:70)
		mockForConstraintsTests(Gateway,[gateway])
		
		def gatewayInstance = new Gateway()
		assertFalse(gatewayInstance.validate())
		assertEquals('nullable', gatewayInstance.errors['prefix'])
		assertEquals('nullable', gatewayInstance.errors['name'])
		assertEquals('nullable', gatewayInstance.errors['queueName'])
		assertEquals('nullable', gatewayInstance.errors['createdBy'])
		
		gatewayInstance.prefix = '6'
		assertFalse(gatewayInstance.validate())
		assertEquals('size', gatewayInstance.errors['prefix'])
		
		gatewayInstance.prefix = '00'
		assertFalse(gatewayInstance.validate())
		assertEquals('unique', gatewayInstance.errors['prefix'])
		
		gatewayInstance.name = 'inter_clickatell'
		assertFalse(gatewayInstance.validate())
		assertEquals('unique', gatewayInstance.errors['name'])
		
		gatewayInstance.queueName = 'openmessenger'
		assertFalse(gatewayInstance.validate())
		assertEquals('unique', gatewayInstance.errors['queueName'])
		
		gatewayInstance.prefix = '66'
		gatewayInstance.name = 'th_dtac'
		gatewayInstance.queueName = 'openmessenger_dtac'
		gatewayInstance.createdBy = 'admin'
		gatewayInstance.messageSize=70
		assertTrue(gatewayInstance.validate())	
		assertEquals(0.0, gatewayInstance.rate)
    }
	
	void testAddToSubscriber(){
		def gateway = new Gateway(prefix:'00', name:'inter_clickatell', queueName:'openmessenger', createdBy:'admin', messageSize:70)		
		
		def subscribers = [new Subscriber(msisdn: '001234567890A', active: 'Y'), 
							new Subscriber(msisdn: '001234567891B', active: 'Y'),
							new Subscriber(msisdn: '001234567892C', active: 'Y')]
		
		mockDomain(Gateway, [gateway])
		mockDomain(Subscriber, subscribers)
		
		gateway.subscribers = subscribers		
		
		gateway.save()
		
		def gatewayInstance = Gateway.get(gateway.id)
		assertNotNull(gatewayInstance)
		assertEquals(3, gatewayInstance.subscribers.size())
	}
	
	void testRemoveFromSubscriber(){
		def gateway = new Gateway(prefix:'00', name:'inter_clickatell', queueName:'openmessenger', createdBy:'admin', messageSize:70)
		
		def subscribers = [new Subscriber(msisdn: '001234567890A', active: 'Y'),
							new Subscriber(msisdn: '001234567891B', active: 'Y'),
							new Subscriber(msisdn: '001234567892C', active: 'Y')]
		
		mockDomain(Gateway, [gateway])
		mockDomain(Subscriber, subscribers)
		
		subscribers.each {
			gateway.addToSubscribers(it)
		}
		
		gateway.save()
		
		def gatewayInstance = Gateway.get(gateway.id)
		assertNotNull(gatewayInstance)
		assertEquals(3, gatewayInstance.subscribers.size())
		
		def subInstance = Subscriber.findByMsisdn('001234567890A')
		assertNotNull subInstance
		
		gatewayInstance.removeFromSubscribers(subInstance)
		gatewayInstance.save()
		assertEquals(2, gatewayInstance.subscribers.size())
		assertEquals(3, Subscriber.count())
		
		/*subInstance = Subscriber.findByMsisdn('001234567891B')
		assertNotNull subInstance
		subInstance.delete()
		assertEquals(2, Subscriber.count())
		assertEquals(1, gatewayInstance.subscribers.size())*/		
	}
	
	void testRemove(){
		def gateway = new Gateway(prefix:'00', name:'inter_clickatell', queueName:'openmessenger', createdBy:'admin', messageSize:70)
		
		def subscribers = [new Subscriber(msisdn: '001234567890A', active: 'Y'),
							new Subscriber(msisdn: '001234567891B', active: 'Y'),
							new Subscriber(msisdn: '001234567892C', active: 'Y')]
		
		mockDomain(Gateway, [gateway])
		mockDomain(Subscriber, subscribers)
		
		subscribers.each {
			gateway.addToSubscribers(it)
		}
		
		gateway.save()
		
		def gatewayInstance = Gateway.get(gateway.id)
		assertNotNull(gatewayInstance)
		assertEquals(3, gatewayInstance.subscribers.size())
		
		def subInstance = Subscriber.findByMsisdn('001234567890A')
		assertNotNull subInstance
		assertEquals('inter_clickatell', gatewayInstance.name)
		assertEquals('openmessenger', gatewayInstance.queueName)
		assertEquals(gatewayInstance.name, subInstance.gateway.name)
		
		gatewayInstance.delete()
		
		assertEquals(3, Subscriber.count())
		assertEquals(0, Gateway.count())
	}
}
