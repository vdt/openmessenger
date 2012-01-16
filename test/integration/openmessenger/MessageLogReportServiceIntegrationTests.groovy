package openmessenger

import grails.test.*
import openmessenger.Event.Status
import openmessenger.Event.Type
import java.text.SimpleDateFormat

class MessageLogReportServiceIntegrationTests extends GroovyTestCase {
	def messageLogReportService
	def previousLog
    protected void setUp() {
        super.setUp()
		MockUtils.mockLogging(MessageLogReportService, true)
		previousLog = MessageLog.count()
		new Event(name: 'The Championships, Wimbledon',
			description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
			occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-25"),
			status:Status.NORMAL, type:Type.EVENT).save()
		new Event(name: 'The Australian Open',
			description: 'The tournament is held in the middle of the Australian summer, in the last fortnight of the month of January; thus an extreme-heat policy is put into play when temperatures reach dangerous levels.',
			occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
			status:Status.NORMAL, type:Type.EVENT).save()			
		
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSearchMessageLogByEvent() {
		def austOpen = Event.findByName('The Australian Open')	
		new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'6612323434213', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-20"), concatinationSize:1, price:2).save()
		new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'6612323434233', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-25"), concatinationSize:1, price:2).save()
		new MessageLog(event:austOpen, eventType:Type.EVENT, msisdn:'6612323434223', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-30"), concatinationSize:1, price:2).save()
		
		//assertEquals(3, Event.count())
		assertEquals(3 + previousLog, MessageLog.count())		
		
		def event = Event.get(1)
		def map = [event:event]
		def results = messageLogReportService.searchMessageLogByCriteria(map)
		assertEquals(2, results.size())
		assertEquals('6612323434213', results.get(0).msisdn)
		assertEquals(event.name, results.get(0).event.name)
		
		
		results = messageLogReportService.searchMessageLogByCriteria([event:austOpen])
		
		assertEquals(1, results.size())
	}
	
	void testSearchMessageLog() {
		new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'6612323434213', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-20"), concatinationSize:1, price:2).save()
		new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'6612323434233', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-25"), concatinationSize:1, price:2).save()
		new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'6612323434223', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-30"), concatinationSize:1, price:2).save()
		
		//assertEquals(3, Event.count())
		assertEquals(3 + previousLog, MessageLog.count())
		def results = messageLogReportService.searchMessageLogByCriteria()
		assertEquals(3 + previousLog, results.size())
	}
	
	void testSearchMessageLogByDate() {
		new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'6612323434213', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-20"), concatinationSize:1, price:2).save()
		new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'6612323434233', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-25"), concatinationSize:1, price:2).save()
		new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'6612323434223', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-30"), concatinationSize:1, price:2).save()
		
		assertEquals(3 + previousLog, MessageLog.count())
		def map = [fromDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-19"), toDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-27")]
		def results = messageLogReportService.searchMessageLogByCriteria(map)
		
		assertEquals(2, results.size())
	}
	
	void testSearchMessageLogByFromDate() {
		new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'6612323434213', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-20"), concatinationSize:1, price:2).save()
		new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'6612323434233', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-25"), concatinationSize:1, price:2).save()
		new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'6612323434223', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-30"), concatinationSize:1, price:2).save()
		
		assertEquals(3 + previousLog, MessageLog.count())
		def map = [fromDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-21")]
		def results = messageLogReportService.searchMessageLogByCriteria(map)
		
		assertEquals(2 + previousLog, results.size())
	}
	
	void testSearchMessageLogByToDate() {
		new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'6612323434213', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-20"), concatinationSize:1, price:2).save()
		new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'6612323434233', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-25"), concatinationSize:1, price:2).save()
		new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'6612323434223', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-30"), concatinationSize:1, price:2).save()
		
		assertEquals(3 + previousLog, MessageLog.count())
		def map = [toDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-25")]
		def results = messageLogReportService.searchMessageLogByCriteria(map)
		
		assertEquals(2, results.size())
	}
	
	void testSearchMessageLogByCriteria() {
		def firstEvent = Event.get(1)
		def secondEvent = Event.findByName('The Australian Open')
		new MessageLog(event:firstEvent, eventType:Type.EVENT, msisdn:'6612323434213', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-20"), concatinationSize:1, price:2).save()
		new MessageLog(event:secondEvent, eventType:Type.EVENT, msisdn:'661232343423', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-25"), concatinationSize:1, price:2).save()
		new MessageLog(event:firstEvent, eventType:Type.EVENT, msisdn:'6612323434223', gateway:'dtac', msg:'test', createBy:'boyone', date:new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-30"), concatinationSize:1, price:2).save()
		
		assertEquals(3 + previousLog, MessageLog.count())
		def map = [event:firstEvent, fromDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-20"), toDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2011-DEC-27")]
		def results = messageLogReportService.searchMessageLogByCriteria(map)
		
		assertEquals(1, results.size())
	}
	
}
