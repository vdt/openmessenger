package openmessenger

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.codehaus.groovy.grails.commons.ApplicationHolder;
import grails.converters.JSON;

class GroupChatController {

    def index = { }
	
	def view = { }
	
	def listAllGroupChat = { }
	
	def listGroupChatSubscribers = { }
	
	def create = { }
	
	def save = { }
	
	def unsubscribeFromGroupChat = { }
	
	def subscribeToGroupChat = { }
	
	// via web
	def sendMessage = { }
	
	def twoWayService
	
	// get message from android
	def chat = {
		
		//is user
		/*def springSecurityService = ApplicationHolder.application.mainContext.getBean('springSecurityService')
		def user = User.findByUsername(params.user)
		//user.getAuthorities()
		def salt = CH.config.grails.plugins.springsecurity.dao.reflectionSaltSourceProperty
		user.password
		def password = springSecurityService.encodePassword(params.password, salt)*/
		def result = twoWayService.hasAuthority(params)
		def msg = [user:params.user, password:params.password, result:result]
		
		render  msg as JSON
	}
}
