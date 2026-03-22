package com.burakcan.aksoy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.burakcan.aksoy.client.RetrieveMessageErrorDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignConfig {

	@Bean
	public ErrorDecoder errorDecoder() {
		return new RetrieveMessageErrorDecoder();
	}
	
	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}
	
}
