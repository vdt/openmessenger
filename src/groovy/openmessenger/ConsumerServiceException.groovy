package openmessenger

class ConsumerServiceException extends RuntimeException {
	String errorMsg
	String senderId
	String msisdn
	
	String toString(){
		errorMsg+' sender_id: '+senderId+' msisdn: '+msisdn
	}
}
