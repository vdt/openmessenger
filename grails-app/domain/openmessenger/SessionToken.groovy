package openmessenger

class SessionToken {
	String username
	String token
	Date issueDate = new Date()

    static constraints = {
		token(unique:true)
    }
}
