package openmessenger

class Event {
    String name
    String description
    Date occuredDate
	Status status
	Type type
	Boolean isSenderId=false
		
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
        occuredDate(nullable: false)
		status(nullable:false, inList:Status.list())
		type(nullable:false, inList:Type.list())
		isSenderId(nullable: false)
    }     
	
    static hasMany = [subscribers:Subscriber, messages:Message]	
    static mapping = { messages sort:'createdDate', order:'desc'}		
}
