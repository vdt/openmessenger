package openmessenger

class Message {
    String title
	String content
    static constraints = {
		title(nullable: false)
		content(nullable: false)
    }
}
