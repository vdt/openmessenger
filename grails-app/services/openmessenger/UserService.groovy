package openmessenger

class UserService {

    static transactional = true

    def getUserByEnabled(Boolean enabled){
		User.findAllByEnabled(enabled)
	}
	
	def getUser(Long userId) {
		User.get(userId)
	}
}
