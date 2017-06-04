package master_spring_mvc.search;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StubTwitterSearchConfig {
	
	@Bean
	public TwitterSearch twitterSearch() {
		return (searchType, keywords) -> Arrays.asList(
				new LightTweet("Treść tweeta"),
				new LightTweet("Treść innego tweeta")
				);
	}

}
