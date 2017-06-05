package master_spring_mvc.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public Executor getAsyncExecutor() {		
		return Executors.newFixedThreadPool(10);
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (ex, method, params) -> logger.error("Nieprzechwycony błąd wywołania asynchronicznego", ex);
	}

}
