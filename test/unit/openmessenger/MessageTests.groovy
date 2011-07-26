package openmessenger


import java.text.SimpleDateFormat  
import grails.test.*

class MessageTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testMessageConstraints() {       
        def newEvent = new Event(name: 'The Championships, Wimbledon',
           description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
           occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
           status: 'NORMAL')
	    mockDomain(Event, [newEvent])   
	
   		def nullMessage = new Message()    
		mockForConstraintsTests(Message, [nullMessage])
		newEvent.addToMessages(nullMessage)
	 	assertFalse nullMessage.validate()           
	
		def validMessage = new Message(title: "the title", content: "the content", createdDate: new Date())
		mockForConstraintsTests(Message, [validMessage])
		newEvent.addToMessages(validMessage)
		assertTrue validMessage.validate()
    }
}
