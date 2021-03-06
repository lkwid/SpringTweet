package master_spring_mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("user").password("user").roles("USER").and()
		.withUser("admin").password("admin").roles("USER", "ADMIN");		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.antMatcher("/api/**")
		.headers().cacheControl().disable()
		.and()
		.httpBasic()
		.and()
		.csrf().disable()
		.authorizeRequests()		
		.antMatchers(HttpMethod.GET, "/api/**").hasRole("USER")
		.antMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
		.anyRequest().authenticated();		
	}	

}
