package openmessenger

import openmessenger.Event.Type
import openmessenger.User

class HomeController {

	def eventService
	def springSecurityService
	
    def index = {
		redirect(action:main)
	}
	
	def main = {
		def userDetails = springSecurityService.principal
		def user = User.get(userDetails.id)
		def events = eventService.findAllEventByUser(user)
		def news = events.findAll { it.type == Type.EVENT }
		def groupChats = events.findAll { it.type == Type.GROUP_CHAT }
		def users = User.list()
		render(view:"main", model:[events: news, groupChats:groupChats, users:users])		
	}
	
}
