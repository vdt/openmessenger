package openmessenger

class ConsumerServiceException extends RuntimeException {
	String message
	String errorcode
	/*String errorMsg
	String senderId
	String msisdn*/
	
	String toString(){
		"ConsumerServiceException[clickatell]: $message"
		//errorMsg+' sender_id: '+senderId+' msisdn: '+msisdn
	}
}
