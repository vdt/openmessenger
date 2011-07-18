package openmessenger

class UserController {
    def authenticate = { 
		def user = User.findByUsernameAndPassword(params.username, params.password)
		session.user = user
		if(user){
			flash.message = "Hello ${user.username}!"
			redirect(controller:"event", action:"list")
		}else{
			flash.message = "Sorry, ${params.username}. Please try again."
			redirect(action:"login")
		}
	}
	
	def logout = {
		session.user = null
		redirect(action:"login")		
	}
}
