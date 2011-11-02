import openmessenger.Event
import openmessenger.Event.Type
import openmessenger.Gateway
import openmessenger.Message
import openmessenger.Subscriber
import openmessenger.User
import openmessenger.Role
import openmessenger.UserRole
import java.text.SimpleDateFormat

class BootStrap {

    def init = { servletContext ->
        if (!Event.count()) {
            def firstEvent = new Event(name: 'Soccernet', 
                description: 'A website that provides comprehensive coverage of world football (soccer)',
                occuredDate: new SimpleDateFormat("yyyy-MMM-dd").parse("1995-DEC-25"),
                subscribers: [	new Subscriber(msisdn: '66809737791', active: 'Y'),
                    new Subscriber(msisdn: '66809737792', active: 'Y'),
                    new Subscriber(msisdn: '66809737793', active: 'Y'),
                    new Subscriber(msisdn: '66809737794', active: 'Y'),
                    new Subscriber(msisdn: '66809737795', active: 'Y')	],
                status: 'NORMAL', type:Type.EVENT).save(failOnError: true)
            
            firstEvent.addToMessages(new Message(title:'Xavi urges Cesc again', 
					content:'Barcelona midfielder Xavi has again weighed into the debate over the future of Arsenal skipper Cesc Fabregas, saying he wants to play alongside his Spain team-mate at club level before he retires.', 
					createdDate:new SimpleDateFormat("yyyy-MMM-dd").parse("2001-DEC-25")))
            firstEvent.addToMessages(new Message(title:'Aguero to fly in and seal City deal', 
					content:'Sergio Aguero is set to fly into Manchester Airport on Wednesday to finalise a transfer that is set to cost Manchester City £38 million. ', 
					createdDate:new SimpleDateFormat("yyyy-MMM-dd").parse("2002-DEC-25")))    
            firstEvent.addToMessages(new Message(title:'Vucinic very close to United', 
					content:'Roma forward Mirko Vucinic is "very close" to joining Manchester United, according to his agent, as chief exec David Gill suggested that United may yet add more personnel this summer. ', 
					createdDate:new SimpleDateFormat("yyyy-MMM-dd").parse("2003-DEC-25")))   
                
		
            /*
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
            */
        }
		
		if(!User.count() && !Role.count()){
			def user = new User(username:'admin', password:'openpubyesroti!', 
								firstname:'admin', lastname:'messenger',
								email:'admin@messenger.opendream.org', enabled:true).save(failOnError: true)
			def role = new Role(authority:'ROLE_ADMINS').save(failOnError: true)
			UserRole.create(user, role)
			new Role(authority:'ROLE_MANAGER').save(failOnError: true)
			new Role(authority:'ROLE_USER').save(failOnError: true)
		}
		
		if(!Gateway.count()){
			new Gateway(prefix:'00', name:'inter_clickatell', queueName:'openmessenger', createdBy:'admin').save(failOnError: true)
			new Gateway(prefix:'66', name:'th_dtac', queueName:'openmessenger_dtac', createdBy:'admin').save(failOnError: true)
		}
    }
    def destroy = {
    }
}
