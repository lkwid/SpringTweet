package master_spring_mvc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import master_spring_mvc.profile.UserProfileSession;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HomeControllerTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void shouldRedirectToProfile() throws Exception {
		this.mockMvc.perform(get("/"))
		.andDo(print())
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/profile"));
	}
	
	@Test
	public void shouldRedirectToTastes() throws Exception {
		MockHttpSession session = new SessionBuilder().userTastes("spring", "groovy").build();		
		this.mockMvc.perform(get("/").session(session))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/search/mixed;keywords=spring,groovy"));
	}
	
	public class SessionBuilder {
		private final MockHttpSession session;
		UserProfileSession sessionBean;
		
		public SessionBuilder() {
			session = new MockHttpSession();
			sessionBean = new UserProfileSession();
			session.setAttribute("scopedTarget.userProfileSession", sessionBean);
		}
		
		public SessionBuilder userTastes(String... tastes) {
			sessionBean.setTastes(Arrays.asList(tastes));
			return this;
		}
		
		public MockHttpSession build() {
			return session;
		}
	}

}
