package openmessenger

class Subscriber {
    String msisdn
    String active

    static constraints = {
        msisdn(nullable: false, size:10..15, unique:true)
        active(nullable: false, inList: ['Y','N'])
    }
    static mapping = {
        sort msisdn:"desc"
    }
}
