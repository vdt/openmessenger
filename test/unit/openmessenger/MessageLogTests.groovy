package openmessenger

import grails.test.*
import openmessenger.Event.Status
import openmessenger.Event.Type
import java.text.SimpleDateFormat
import java.util.regex.Matcher


class MessageLogTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
		
		def eventInstances = [new Event(name: 'The Championships, Wimbledon',
			description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
			occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("20011-DEC-25"),
			status:Status.NORMAL, type:Type.GROUP_CHAT)]
		mockDomain(Event, eventInstances)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidate() {
		def msgHis = new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'661232343423', gateway:'dtac', msg:'test', createBy:'boyone', date:new Date(), concatinationSize:1, price:2)
		mockForConstraintsTests(MessageLog, [msgHis])
		assertTrue(msgHis.validate())
    }
	
	void testSave(){
		def msgHis = new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'661232343423', gateway:'dtac', msg:'test', createBy:'boyone', date:new Date(), concatinationSize:1, price:2)
		mockDomain(MessageLog, [msgHis])
		msgHis.save()
		assertEquals(1, MessageLog.count())
		assertEquals(1, msgHis.id)
	}
	
	void testRemove(){
		def msgHis = new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'661232343423', gateway:'dtac', msg:'test', createBy:'boyone', date:new Date(), concatinationSize:1, price:2)
		def msgHis2 = new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'661232343423', gateway:'dtac', msg:'test', createBy:'boyone', date:new Date(), concatinationSize:1, price:2)
		
		mockDomain(MessageLog, [msgHis, msgHis2])
		
		assertEquals(2, MessageLog.count())
		assertEquals(1, msgHis.id)
		assertEquals(2, msgHis2.id)
		
		msgHis2.delete()
		assertEquals(1, MessageLog.count())
	}
	
	void testOrder(){
		def msgHis = new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'661232343423', gateway:'dtac', msg:'test', createBy:'boyone', date:new Date(), concatinationSize:1, price:2)
		def msgHis2 = new MessageLog(event:Event.get(1), eventType:Type.EVENT, msisdn:'661232343424', gateway:'dtac', msg:'test', createBy:'boyone', date:new Date(), concatinationSize:1, price:2)
		
		mockDomain(MessageLog, [msgHis, msgHis2])
		
		def list = MessageLog.list()
		def msgLog = list.get(1)
		
		assertEquals('661232343424', msgLog.msisdn)
	}
	
	void testString(){
		def str = "from xxx x where"
		assertEquals(true, (str =~ /(where)$/).find())
		
		assertEquals(false, ("from xxx x wherex" =~ /(where)$/).find())
		//def m= java.util.regex.Pattern.compile( /(where)$/ ).matcher( str )
		//Matcher m = str =~ /(where)$/
		//println m.find()
	}
}
