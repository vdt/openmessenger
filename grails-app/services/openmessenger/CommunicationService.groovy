package openmessenger

import java.awt.Event;
import openmessenger.Event.Type
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class CommunicationService {

    static transactional = false
	def springSecurityService

    Boolean hasAuthority(def username, def password) {
		// = ApplicationHolder.application.mainContext.getBean('springSecurityService')
		def user = User.findByUsername(username)
		//user.getAuthorities()
		def salt = CH.config.grails.plugins.springsecurity.dao.reflectionSaltSourceProperty		
		def pwd = springSecurityService.encodePassword(password, salt)
		
		user.password == pwd
    }
	
	def extractMessage(def eventId, def msisdn, def content){ //extract codeName(G,P), msg// code, msisdn, content
		def event = eventId!='null'?getCommunicationTypeInstanceByCodeName(eventId, getType(eventId), msisdn):getCommunicationTypeInstanceByMsisdn(Type.GROUP_CHAT, msisdn)	
			
		if(!event) throw new CommunicationException(message:"msisdn:$msisdn, content:$content, eventId:$eventId")
			
		def message = new Message(title:event.name, content:content.trim(), createdDate: new Date(), createBy:msisdn)
		
		return [eventId:event?.id, message:message]
	}
	
	def getType(def code){
		def type = code?.size()?code?.substring(0,1):'';
		type=='g'?Type.GROUP_CHAT:type=='p'?Type.POLL:'undefined'
	}
	
	def getCommunicationTypeInstanceByCodeName(def code, def type, def msisdn){
		def instance
		if(type==Type.GROUP_CHAT)
			instance = GroupChat.findByCodename(code)
		else if(type==Type.POLL)
			instance = Poll.findByCodename(code)
			
		return instance?.subscribers?.find {it.msisdn ==  msisdn}?instance:null
	}
	
	def getCommunicationTypeInstanceByMsisdn(def type, def msisdn){
		def instances
		def instance
		if(type==Type.GROUP_CHAT){
			instances = getGroupChatInstance(msisdn)			
		}else if(type==Type.POLL){
			instances = getPollInstance(msisdn)
		}
		
		if(instances.size()==1){
			instance = instances.get(0)			
		}else if(instances.size()>1){
			throw new CommunicationException(message:"msisdn:$msisdn, msg:get multiple ${type.toString()}")
		}
		
		return instance
	}
	
	def getGroupChatInstance(def msisdn){
		def group = GroupChat.createCriteria()
		def instance = group.list {
			subscribers{
				eq('msisdn', msisdn)
			}
			and{
				eq('type',Type.GROUP_CHAT)
			}			
		}
		instance?:null
	}
	
	def getPollInstance(def msisdn){
		def poll = Poll.createCriteria()
		def instance = poll.list {
			subscribers{
				eq('msisdn', msisdn)
			}
			and{
				eq('type',Type.POLL)
			}
		}
		instance?:null
	}
}
