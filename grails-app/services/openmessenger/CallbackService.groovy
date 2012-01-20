package openmessenger

import grails.validation.ValidationException

class CallbackService {

    static transactional = true
	static rabbitQueue = 'eventCallback'

    void handleMessage(Map map) {
		try {
			println "callback is called"
			// todo insert to db -> queuename, errorcode, senderId, msisdn, content, sendDate
			createCallbackMessage(map)
		} catch(ValidationException e) {
			log.error( "event:${map?.eventId}, queuename:${map?.queuename}, errorcode:${map?.errorcode}, senderId:${map?.senderId}, msisdn:${map?.msisdn}, content:${map?.content}, sendDate:${map?.sendDate}")
			log.error e
		} catch(e) {
			log.error( "event:${map?.eventId}, queuename:${map?.queuename}, errorcode:${map?.errorcode}, senderId:${map?.senderId}, msisdn:${map?.msisdn}, content:${map?.content}, sendDate:${map?.sendDate}")
			log.error e
		}
    }
	
	def createCallbackMessage(Map map) {
		new CallbackMessage(event:Event.get(map.eventId), queuename:map.queuename, errorcode:map.errorcode, senderId:map.senderId,
			msisdn:map.msisdn, content:map.content, sendDate:map.sendDate).save(failOnError:true)
	}
}
