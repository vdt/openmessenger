package openmessenger

class MessageLogReportService {

    static transactional = true

	/*
	 * map params
	 * event = Event
	 * fromDate = Date
	 * toDate = Date
	 */
    def searchMessageLogByCriteria(Map map) {
		def criteriaMap = [:]				
		def gsql = "from MessageLog as m"
		if(map?.event || map?.fromDate || map?.toDate){
			gsql += " where"
		}
		if(map?.event){
			if(!(gsql =~ /(where)$/).find()) {
				gsql += " and"
			}			
			gsql += " m.event=:event"
			criteriaMap.event = map.event
		}
		if(map?.fromDate){
			if(!(gsql =~ /(where)$/).find()) {
				gsql += " and"
			}
			gsql += " m.date>=:fromDate"
			criteriaMap.fromDate = map.fromDate
		}
		if(map?.toDate){
			if(!(gsql =~ /(where)$/).find()) {
				gsql += " and"
			}
			gsql += " m.date<=:toDate"
			criteriaMap.toDate = map.toDate
		}
		gsql += " order by m.date"
		log.debug("sql "+gsql)
		log.debug("map "+criteriaMap)
		
		return MessageLog.findAll(gsql, criteriaMap)
	}
}
