package openmessenger

class CallbackMessage {
	
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
