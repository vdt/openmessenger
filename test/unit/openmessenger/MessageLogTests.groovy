package openmessenger

import grails.test.*

import openmessenger.Event.Type

class MessageLogTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidate() {
		def msgHis = new MessageLog(eventId:1, eventType:Type.EVENT, msisdn:'661232343423', gateway:'dtac', msg:'test', createBy:'boyone', date:new Date(), concatinationSize:1, price:2)
		mockForConstraintsTests(MessageLog, [msgHis])
		assertTrue(msgHis.validate())
    }
	
	void testSave(){
		def msgHis = new MessageLog(eventId:1, eventType:Type.EVENT, msisdn:'661232343423', gateway:'dtac', msg:'test', createBy:'boyone', date:new Date(), concatinationSize:1, price:2)
		mockDomain(MessageLog, [msgHis])
		msgHis.save()
		assertEquals(1, MessageLog.count())
		assertEquals(1, msgHis.id)
	}
	
	void testRemove(){
		def msgHis = new MessageLog(eventId:1, eventType:Type.EVENT, msisdn:'661232343423', gateway:'dtac', msg:'test', createBy:'boyone', date:new Date(), concatinationSize:1, price:2)
		def msgHis2 = new MessageLog(eventId:1, eventType:Type.EVENT, msisdn:'661232343423', gateway:'dtac', msg:'test', createBy:'boyone', date:new Date(), concatinationSize:1, price:2)
		
		mockDomain(MessageLog, [msgHis, msgHis2])
		
		assertEquals(2, MessageLog.count())
		assertEquals(1, msgHis.id)
		assertEquals(2, msgHis2.id)
		
		msgHis2.delete()
		assertEquals(1, MessageLog.count())
	}
	
	void testOrder(){
		def msgHis = new MessageLog(eventId:1, eventType:Type.EVENT, msisdn:'661232343423', gateway:'dtac', msg:'test', createBy:'boyone', date:new Date(), concatinationSize:1, price:2)
		def msgHis2 = new MessageLog(eventId:1, eventType:Type.EVENT, msisdn:'661232343424', gateway:'dtac', msg:'test', createBy:'boyone', date:new Date(), concatinationSize:1, price:2)
		
		mockDomain(MessageLog, [msgHis, msgHis2])
		
		def list = MessageLog.list()
		def msgLog = list.get(1)
		
		assertEquals('661232343424', msgLog.msisdn)
	}
}
