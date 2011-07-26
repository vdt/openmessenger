package openmessenger

class SubscriberController {

    def listAllSubscribers = {
        def subscribers = Subscriber.list()
        render(template:"listAllSubscribers",model:[subscribers: subscribers])
    }    

}
