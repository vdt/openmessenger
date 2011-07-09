package openmessenger

class Event {
    String name
    String description
    Date occuredDate
    String status
	
    static hasMany = [subscribers:Subscriber]
    static constraints = {
        name(nullable: false)
        description(nullable: false)
        occuredDate(nullale: false)
        status(nullable:false, inList:['NORMAL', 'CRITICAL', 'STABLE', 'RELIVE'])
    }

}
