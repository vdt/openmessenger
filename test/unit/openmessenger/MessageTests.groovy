package openmessenger

import grails.test.*

class MessageTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testMessageConstraints() {                                        
	   	def nullMessage = new Message()    
		mockForConstraintsTests(Message, [nullMessage])
	 	assertFalse nullMessage.validate()           
	
		def validMessage = new Message(title: "the title", content: "the content")
		mockForConstraintsTests(Message, [validMessage])
		assertTrue validMessage.validate()
    }
}
