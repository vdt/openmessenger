package openmessenger

class Event {
    String name
    String description
    Date occuredDate
	Status status
		
	public enum Status{
		NORMAL,
		CRITICAL,
		STABLE,
		RELIVE
		static list() {
			[NORMAL,CRITICAL,STABLE,RELIVE]
		}
	}
	
	public enum Type{
		EVENT,
		GROUP_CHAT,
		POLL
		static list(){
			[EVENT,GROUP_CHAT,POLL]
		}
	}
	
	static constraints = {
        name(nullable: false)
        description(nullable: false)
        occuredDate(nullale: false)
		status(nullable:false, inList:Status.list())
        //status(nullable:false, inList:['NORMAL', 'CRITICAL', 'STABLE', 'RELIVE'])
    } 
    
    static hasMany = [subscribers:Subscriber, messages:Message] 
    static mapping = { messages sort:'createdDate', order:'desc'}
}
