package master_spring_mvc.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class CacheConfiguration {
	@Bean
	public CacheManager cacheManager() {
		GuavaCacheManager cacheManager = new GuavaCacheManager("searches");
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
		cacheBuilder.softValues().expireAfterWrite(10, TimeUnit.MINUTES);
		cacheManager.setCacheBuilder(cacheBuilder);
		return cacheManager;		
	}
}
