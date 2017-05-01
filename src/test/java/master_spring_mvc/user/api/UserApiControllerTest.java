package master_spring_mvc.user.api;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import master_spring_mvc.user.User;
import master_spring_mvc.user.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserApiControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private UserRepository userRepository;	
	private MockMvc mockMvc;	
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.wac)
				.apply(springSecurity())
				.build();
		userRepository.reset(new User("lkwid@github.io"));
	}

	@Test
	public void shouldListUsers() throws Exception {
		this.mockMvc.perform(get("/api/users").with(user("admin").password("admin").roles("USER", "ADMIN")).accept(MediaType.APPLICATION_JSON))			
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].email", is("lkwid@github.io"))
			);		
	}

}
