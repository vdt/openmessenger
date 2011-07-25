package openmessenger

class ConsumerServiceException extends Exception {
	String errorMsg
	String senderId
	String msisdn
	
	public ConsumerServiceException(){
		super()
	}
	
	public ConsumerServiceException(String errorMsg, String senderId, String msisdn){
		super(errorMsg+' sender_id: '+senderId+' msisdn: '+msisdn)
		
		this.errorMsg = errorMsg
		this.senderId = senderId
		this.msisdn = msisdn		
	}
	
	public ConsumerServiceException(Map map){
		super(map.errorMsg+' sender_id: '+map.senderId+' msisdn: '+map.msisdn)
		
		errorMsg = map.errorMsg
		senderId = map.senderId
		msisdn = map.msisdn	
	}
	
	String toString(){
		errorMsg+' sender_id: '+senderId+' msisdn: '+msisdn
	}
}
