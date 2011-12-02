package openmessenger

import grails.converters.JSON

class ReportController {
	def eventService
	def messageLogReportService
	def springSecurityService

    def index = { 
		def userDetails = springSecurityService.principal
		def user = User.get(userDetails?.id)
		def events = eventService.findAllEventByUser(user)
		[events:events]
	}
	
	def searchMessageLogs = {
		
		def results
		render results as JSON
	}
}
