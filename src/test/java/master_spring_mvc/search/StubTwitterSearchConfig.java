package master_spring_mvc.search;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class StubTwitterSearchConfig {
	
	@Primary @Bean
	public TwitterSearch twitterSearch() {
		return (searchType, keywords) -> Arrays.asList(
				new LightTweet("Treść tweeta"),
				new LightTweet("Treść innego tweeta")
				);
	}

}
