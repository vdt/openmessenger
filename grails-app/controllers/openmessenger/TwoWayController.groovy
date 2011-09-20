package openmessenger

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.codehaus.groovy.grails.commons.ApplicationHolder;
import grails.converters.JSON;

class TwoWayController {

    def twoWayService
	
	// get message from android
	def chat = { 
		def result = twoWayService.hasAuthority(params)
		def msg = [user:params.user, password:params.password, result:result?:null]
		
		render  msg as JSON	
	}
}
