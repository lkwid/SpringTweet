package master_spring_mvc.controller;

import static org.junit.Assert.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import master_spring_mvc.MasterSpringMvcApplication
import master_spring_mvc.profile.UserProfileSession
import master_spring_mvc.search.StubTwitterSearchConfig
import spock.lang.Specification

@ContextConfiguration (
classes = [MasterSpringMvcApplication, StubTwitterSearchConfig]
)

@SpringBootTest
class HomeControllerSpec extends Specification {

	@Autowired
	WebApplicationContext wac;
	MockMvc mockMvc;

	def setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	def "Użytkownik podczas pierwszej wizyty jest kierowany na stronę profilu"() {
		when: "Otwieram stronę główną"
		def response = mockMvc.perform(get("/"))
		then: "Jestem kierowany na stronę profilu"
		response
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/profile"))
	}

	def "Użytkownik po wprowadzeniu zainteresowań jest kierowany na stronę wyników"() {
		MockHttpSession session = new SessionBuilder().userTastes("opera", "bf1").build()
		when: "Otwieram sesję"
		def response = mockMvc.perform(get("/").session(session))
		then: "Jestem kierowany na stronę wyników"
		response
			.andExpect(status().isFound())			
			.andExpect(redirectedUrl("/search/mixed;keywords=opera,bf1"))		
	}

	class SessionBuilder {
		MockHttpSession session
		UserProfileSession sessionBean
		
		SessionBuilder() {
			session = new MockHttpSession()
			sessionBean = new UserProfileSession()
			session.setAttribute("scopedTarget.userProfileSession", sessionBean)
		}
		
		SessionBuilder userTastes(String... tastes) {
			sessionBean.setTastes(Arrays.asList(tastes))
			return this		
		}

		def build() {
			return session
		}
	}
	
}
