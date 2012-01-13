package openmessenger

import java.security.SecureRandom

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.springframework.security.core.codec.Base64;

class RemoteAuthenticationService {

    static transactional = true
	def springSecurityService
	def tokenLength = 17
	def tokenValiditySeconds = 900 // 15 minutes
	SecureRandom random = new SecureRandom()

    def authenticate(String username, String password) {
		def user = User.findByUsername(username)
		def salt = CH.config.grails.plugins.springsecurity.dao.reflectionSaltSourceProperty
		def remotePassword = springSecurityService.encodePassword(password, salt)
		
		log.debug("${user?.password} : $remotePassword")
		if(user?.password.equals(remotePassword)) {
			removeExpiredSessionToken(username)
			return createNewToken(username)
		} else {
			return null
		}
    }
	
	def hasSessionToken(String username, String token) {
		def sessionToken = getSessionToken(username, token)
		def enable = isEnable(sessionToken)
		
		if(sessionToken && enable) {
			return true
		} else if(!enable && sessionToken) {
			sessionToken.delete()
			return false
		} else {
			return false
		}
	}
	
	def createNewToken(String username) {
		String token = generateTokenData()
		def sessionToken = new SessionToken(username:username, token:token)
		sessionToken.save()
		return sessionToken.token
	}
	
	def getSessionToken(String username, String token) {
		return SessionToken.findByUsernameAndToken(username, token)
	}
	
	def isEnable(def sessionToken) {
		if(sessionToken) {
			return sessionToken.issueDate.getTime() + (tokenValiditySeconds * 1000L) > System.currentTimeMillis()
		} else {
			return false
		}
	}
	
	def removeExpiredSessionToken(String username) {
		def sessionTokens = SessionToken.findAllByUsername(username)
		
		sessionTokens.each {
			if(!isEnable(it)) {
				it.delete() 		
			}	
		}		
	}
	
	protected String generateTokenData() {
		byte[] newToken = new byte[tokenLength];
		random.nextBytes(newToken);
		return new String(Base64.encode(newToken));
	}
}
