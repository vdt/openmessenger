package openmessenger

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.codehaus.groovy.grails.commons.ApplicationHolder;
import grails.converters.JSON;

class GroupChatController {
	def eventService
	
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
}
