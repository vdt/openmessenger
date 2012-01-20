package openmessenger

import org.grails.jaxrs.itest.IntegrationTestCase;
import org.junit.Test

import grails.converters.JSON;
import grails.test.*
import openmessenger.Event.Status
import openmessenger.Event.Type
import static org.junit.Assert.*
import openmessenger.EventDTO

class UserResourceIntegrationTest extends IntegrationTestCase {
	
	void testApiUser() {
		def user1 = new User(username:'boyone11',
			password:'password',
			firstname:'boyone11',
			lastname:'lastname',
			email:'email@email.com',
			enabled:true,
			accountExpired:false,
			accountLocked:false,
			passwordExpired:false
		)
		user1.save()
		
		def headers = ['Content-Type':'application/json', 'Accept':['application/json', 'text/plain']]
		
		/*
		 *  test PUT /api/user 
		 */
		user1.firstname = 'boyone13'
		def juser = user1 as JSON
		juser = juser.toString()
		println juser
		sendRequest('/api/user','PUT', headers, juser.bytes)
		assertEquals(200, response.status)
		assertTrue(juser, response.contentAsString.contains('"firstname":"boyone13"'))
		assertEquals('boyone13', User.get(user1.id).firstname)
		
		
		/* 
		 * test POST /api/user
		 */
		//def map = '{"class":"xxxx", "username":"userx", "password":"password"}' //java.util.Map
		def map = [class:'xxxx', username:'userx', password:'password', firstname:'boyone111',
			lastname:'lastname',
			email:'email@email.com',
			enabled:true,
			accountExpired:false,
			accountLocked:false,
			passwordExpired:false]
		map = map as JSON
		sendRequest('/api/user','POST', headers, map.toString().bytes)
		assertEquals(200, response.status)
		println response.contentAsString
		assertEquals('boyone111', User.findByUsername('userx').firstname)
	}
}
