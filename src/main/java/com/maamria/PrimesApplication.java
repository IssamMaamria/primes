package com.maamria;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.Executor;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@EnableAsync
public class PrimesApplication extends WebMvcConfigurerAdapter implements AsyncConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(PrimesApplication.class, args);
	}

	@Bean
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		executor.setCorePoolSize(availableProcessors + 1);
		executor.setMaxPoolSize(availableProcessors + 1);
		executor.setThreadNamePrefix("primes-calc");
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}
}
