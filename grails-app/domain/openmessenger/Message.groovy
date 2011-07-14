package openmessenger

class Message {
    String title
	String content     
	Date createdDate
    static constraints = {
		title(nullable: false)
		content(nullable: false)
		createdDate(nullable: false)
    }
}
