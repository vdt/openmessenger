package openmessenger

import java.awt.Event;
import openmessenger.Event.Type
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class TwoWayService {

    static transactional = false
	def springSecurityService

    Boolean hasAuthority(def params) {
		// = ApplicationHolder.application.mainContext.getBean('springSecurityService')
		def user = User.findByUsername(params.user)
		//user.getAuthorities()
		def salt = CH.config.grails.plugins.springsecurity.dao.reflectionSaltSourceProperty		
		def password = springSecurityService.encodePassword(params.password, salt)
		
		user.password == password
    }
	
	def extractMsg(def params){ //extract codeName(G,P), msg
		def msisdn = params.msisdn
		String text = params.msg.trim()	
		def codeIndex = text.indexOf('#')
		def msgIndex = text.indexOf(' ')
		def code = codeIndex==0?text.substring(codeIndex+1, msgIndex).toLowerCase():null		
		def msg = code?text.substring(msgIndex).trim():text
		def type = code?getType(code):Type.GROUP_CHAT
		
		return [code:code, msg:msg, msisdn:msisdn, type:type]
	}
	
	def getType(def code){
		def type = code.substring(0,1);
		type=='g'?Type.GROUP_CHAT:type=='p'?Type.POLL:'undefine'
	}
	
	def getCommunicationTypeInstanceByCodeName(def code, def type, def msisdn){
		def instance;
		if(type==Type.GROUP_CHAT)
			instance = GroupChat.findByCodeName(code)
		else if(type==Type.POLL)
			instance = Poll.findByCodeName(code)
		return instance?.subscribers?.find { it.msisdn ==  msisdn}?instance:null
	}
	
	def getCommunicationTypeInstanceByMsisdn(def type, def msisdn){
		def instance;
		if(type==Type.GROUP_CHAT)
			instance = getGroupChatInstance(msisdn)
		else if(type==Type.POLL)
			instance = getPollInstance(msisdn)
		return instance
	}
	
	def getGroupChatInstance(def msisdn){
		def group = GroupChat.createCriteria()
		def instance = group.list {
			subscribers{
				eq('msisdn', msisdn)
			}
		}
		instance	
	}
	
	def getPollInstance(def msisdn){
		def poll = Poll.createCriteria()
		def instance = poll.list {
			subscribers{
				eq('msisdn', msisdn)
			}
		}
		instance
	}
}
