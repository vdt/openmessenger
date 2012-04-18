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
		def prefix = params.prefix?: ''

		def news = events.findAll { it.type == Type.EVENT }		
		def totalEvents = news?.size()?:0
		def offset = 0
		def max = 10
		if(params.id=='event') {
			offset = params.int(prefix+'offset')
			max = params.int(prefix+'max')				
		}
		if(offset+max>totalEvents && totalEvents > 0) {
			max =totalEvents-offset			
		}
		news = news[offset..offset+max-1]

		def groupChats = events.findAll { it.type == Type.GROUP_CHAT }
		def totalGroupChats = groupChats?.size()?:0
		def goffset = 0
		def gmax = 10
		if(params.id=='group') {
			goffset = params.int(prefix+'offset')
			gmax = params.int(prefix+'max')			
		}
		if(goffset+gmax>totalGroupChats && totalGroupChats > 0) {
			gmax =totalGroupChats-goffset			
		}
		groupChats = groupChats[goffset..goffset+gmax-1]
		
		render(view:"main", model:[events: news, totalEvents: totalEvents, groupChats:groupChats, totalGroupChats: totalGroupChats, tab:params.id, offset:offset, goffset:goffset])		
	}
	
}
