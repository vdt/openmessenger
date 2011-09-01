package openmessenger

class Event {
    String name
    String description
    Date occuredDate
    String status	
	
    static constraints = {
        name(nullable: false)
        description(nullable: false)
        occuredDate(nullale: false)
        status(nullable:false, inList:['NORMAL', 'CRITICAL', 'STABLE', 'RELIVE'])
    } 
    
    static hasMany = [subscribers:Subscriber, messages:Message] 
    static mapping = { messages sort:'createdDate', order:'desc'}
}
