package openmessenger

class User {

	transient springSecurityService

	String username
	String password
	
	String firstname
	String lastname
	String email
	
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	//static transients = ['authorities']

	static constraints = {
		username blank: false, unique: true
		password blank: false
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}
	
	Set<Event> getEvents() {
		UserEvent.findAllByUser(this).collect { it.event } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
