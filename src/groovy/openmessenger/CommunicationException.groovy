/**
 * 
 */
package openmessenger

/**
 * @author boyone
 *
 */
class CommunicationException extends RuntimeException {
	String message

	String toString() {
		return message;
	}	
}
