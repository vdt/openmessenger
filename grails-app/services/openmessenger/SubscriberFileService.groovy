package openmessenger

class SubscriberFileService {

    static transactional = true

    List parseCsv(File file) {
		def msisdns = []
		file.eachCsvLine { tokens ->
			def msisdn = tokens[0].toString().trim()
			msisdn = msisdn.replace(' ', '')
			msisdns.add(msisdn)
		}
		return msisdns
    }
}
