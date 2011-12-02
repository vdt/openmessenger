package openmessenger

import grails.test.*
import openmessenger.Event.Status
import openmessenger.Event.Type
import java.text.SimpleDateFormat

class MessageLogReportServiceTests extends GrailsUnitTestCase {
	protected void setUp() {
        super.setUp()
		def eventInstances = [new Event(name: 'The Championships, Wimbledon',
			description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
			occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
			status:Status.NORMAL, type:Type.GROUP_CHAT),
		new Event(name: 'The Australian Open',
			description: 'The tournament is held in the middle of the Australian summer, in the last fortnight of the month of January; thus an extreme-heat policy is put into play when temperatures reach dangerous levels.',
			occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
			status:Status.NORMAL, type:Type.GROUP_CHAT)]
	
		mockDomain(Event, eventInstances)		
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testAddAndRetrieveMessageLog() {
		assertEquals 2, Event.list().size()
		
		def msgHis = new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'661232343421', gateway:'dtac', msg:'test', createBy:'boyone', date:new Date(), concatinationSize:1, price:2)
		def msgHis2 = new MessageLog(event:Event.get(2), eventType:Type.EVENT, msisdn:'661232343423', gateway:'dtac', msg:'test', createBy:'boyone', date:new Date(), concatinationSize:1, price:2)
		
		mockDomain(MessageLog, [msgHis, msgHis2])
		
		assertEquals(2, MessageLog.count())
		
		def msgHistoryInstance = MessageLog.get(msgHis.id)
		assertEquals('661232343421', msgHistoryInstance.msisdn)
		assertEquals('The Championships, Wimbledon', msgHistoryInstance.event.name)
    }	
}
