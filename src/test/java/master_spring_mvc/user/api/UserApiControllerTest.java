package master_spring_mvc.user.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import master_spring_mvc.utils.JsonUtil;

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
	
	@Test
	public void shouldCreateNewUser() throws Exception {
		User user = new User("duke1up@gmail.com");
		this.mockMvc.perform(
				post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(user))
				.with(httpBasic("admin", "admin"))
				)
			.andExpect(status().isCreated());
		assertThat(userRepository.findAll())
			.extracting(User::getEmail)
			.containsOnly("duke1up@gmail.com", "lkwid@github.io");
	}
	
	@Test
	public void shouldDeteleUser() throws Exception {
		this.mockMvc.perform(
				delete("/api/users/lkwid@github.io")
					.accept(MediaType.APPLICATION_JSON)
					.with(httpBasic("admin", "admin"))
					)
			.andExpect(status().isOk());
		assertThat(userRepository.findAll()).hasSize(0);
	}
	
	@Test
	public void shouldReturnNotFoundWhenDeletingUnknownUser() throws Exception {
		this.mockMvc.perform(
				delete("/api/users/unknown@github.io")
					.accept(MediaType.APPLICATION_JSON)
					.with(httpBasic("admin", "admin"))
				)
			.andExpect(status().isNotFound());		
	}
	
	@Test
	public void shouldUpdateExistingUser() throws Exception {
		User user = new User("duke1up@github.io");
		this.mockMvc.perform(
				put("/api/users/lkwid@github.io")
					.content(JsonUtil.toJson(user))
					.contentType(MediaType.APPLICATION_JSON)
					.with(httpBasic("admin", "admin"))
				)
			.andExpect(status().isOk());
		assertThat(userRepository.findAll())
			.extracting(User::getEmail)
			.containsOnly("lkwid@github.io");
	}
				
		
}


