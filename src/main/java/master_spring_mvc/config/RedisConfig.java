package master_spring_mvc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@Profile("redis")
@EnableRedisHttpSession
public class RedisConfig {
	@Bean("objectRedisTemplate")
	public RedisTemplate<Object, Object> ojectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}
	
	@Primary @Bean
	public CacheManager cacheManager(@Qualifier("objectRedisTemplate") RedisTemplate<Object, Object> template) {
		RedisCacheManager cacheManager = new RedisCacheManager(template);
		cacheManager.setCacheNames(Arrays.asList("searches"));
		cacheManager.setDefaultExpiration(36000);
		return cacheManager;
	}
}
