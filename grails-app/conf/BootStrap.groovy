import openmessenger.Event
import openmessenger.Message
import openmessenger.Subscriber
import java.text.SimpleDateFormat

class BootStrap {

    def init = { servletContext ->
        if (!Event.count()) {
            def firstEvent = new Event(name: 'The Championships, Wimbledon', 
                description: 'The oldest tennis tournament in the world, considered by many to be the most prestigious',
                occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
                subscribers: [	new Subscriber(msisdn: '66809737791', active: 'Y'),
                    new Subscriber(msisdn: '66809737792', active: 'Y'),
                    new Subscriber(msisdn: '66809737793', active: 'Y'),
                    new Subscriber(msisdn: '66809737794', active: 'Y'),
                    new Subscriber(msisdn: '66809737795', active: 'Y')	],
                status: 'NORMAL').save(failOnError: true)
            
            firstEvent.addToMessages(new Message(title:'1', content:'1', createdDate:new SimpleDateFormat("yyyy-MMM-dd").parse("2001-DEC-25")))
            firstEvent.addToMessages(new Message(title:'2', content:'3', createdDate:new SimpleDateFormat("yyyy-MMM-dd").parse("2002-DEC-25")))    
            firstEvent.addToMessages(new Message(title:'3', content:'3', createdDate:new SimpleDateFormat("yyyy-MMM-dd").parse("2003-DEC-25")))   
                
		
            
            def secondEvent = new Event(name: 'The Australian Open', 
                description: 'The tournament is held in the middle of the Australian summer, in the last fortnight of the month of January; thus an extreme-heat policy is put into play when temperatures reach dangerous levels.',
                occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25"),
                subscribers: [	new Subscriber(msisdn: '66809737781', active: 'Y'),
                    new Subscriber(msisdn: '66809737782', active: 'Y'),
                    new Subscriber(msisdn: '66809737783', active: 'Y'),
                    new Subscriber(msisdn: '66809737784', active: 'Y'),
                    new Subscriber(msisdn: '66809737785', active: 'Y')	],                
                status: 'NORMAL').save(failOnError: true)
            
            secondEvent.addToMessages(new Message(title:'1', content:'1', createdDate:new SimpleDateFormat("yyyy-MMM-dd").parse("2001-DEC-25")))
            secondEvent.addToMessages(new Message(title:'2', content:'3', createdDate:new SimpleDateFormat("yyyy-MMM-dd").parse("2002-DEC-25")))    
            secondEvent.addToMessages(new Message(title:'3', content:'3', createdDate:new SimpleDateFormat("yyyy-MMM-dd").parse("2003-DEC-25")))             
            
        }
    }
    def destroy = {
    }
}
