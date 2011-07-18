package openmessenger

class User {
    String username
	String password  
	String role ="user"
	Date createedDate
    static constraints = {
		username(matches: "[a-zA-Z]+", nullable: false)
		password(matches: /^.*(?=.{6,})(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$/, 
					size:6..15, nullable: false, password: true)
		role(inList:["admin", "user"])			
		createedDate(nullable: true)
    }    
}
/*
Must be at least 10 characters
Must contain at least one one lower case letter, one upper case letter, one digit and one special character
Valid special characters are -   @#$%^&+=
*/