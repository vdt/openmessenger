/**
 * 
 */
package openmessenger

/**
 * @author boyone
 *
 */
class Poll extends GroupChat {
	Date startDate
	Date endDate
	
	static constraints = {
		startDate(nullable:true)
		endDate(nullable:true)
	}
}
