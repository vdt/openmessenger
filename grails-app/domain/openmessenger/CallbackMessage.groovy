package openmessenger

class CallbackMessage {
	Event event
	String queuename
	String errorcode
	String senderId
	String msisdn
	String content
	Date sendDate

    static constraints = {
		queuename(nullable:false)
		senderId(nullable:true)
    }
}
