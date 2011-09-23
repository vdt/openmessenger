package openmessenger

import org.grails.jaxrs.itest.IntegrationTestCase
import org.junit.Test

import static org.junit.Assert.*


class EventResourceTests extends IntegrationTestCase {
	@Test
    void testGetEventRepresentation() {
        def headers = ['Content-Type':'text/plain', 'Accept':'text/plain']
        def content = '{"class":"hello.Person","firstName":"Sam","lastName":"Hill"}'
        def expected = 'Event'

        sendRequest('/api/event/G1/msisdn/66816898892/hello/passphase/roofimon/passw0rd','GET', headers)
        
        assertEquals(200, response.status)
        assertEquals("${expected}".toString(), response.contentAsString)
    }
}
