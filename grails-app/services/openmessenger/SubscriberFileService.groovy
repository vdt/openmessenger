package openmessenger

class SubscriberFileService {

    static transactional = true

    List parseCsv(File file) {
		def subscribers = []
		file.eachCsvLine { tokens ->
			subscribers.add(tokens[0].toString().trim())
		}
		return subscribers
    }
}
