package openmessenger

class Gateway {
	String prefix
	String name
	String queueName
	Float rate = 0.0
	Integer messageSize = 70
	String createdBy
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [subscribers:Subscriber]

    static constraints = {
		prefix(nullable: false, size:2..2, unique:true)
		name(nullable: false, size:1..30, unique:true)
		queueName(nullable: false, size:1..30, unique:true)	
		rate(nullable: true)	
		messageSize(nullable: false)
    }
}
