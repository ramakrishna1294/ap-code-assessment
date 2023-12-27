package com.ap.code.assessment.exception.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ApiErrorMessageConfig {
	
	public static final  Logger logger = LoggerFactory.getLogger(ApiErrorMessageConfig.class);
	
	@Bean
	public MessageSource apiErrorMessageSource() {
		logger.info("Entering apiErrorMessageSource");
		ReloadableResourceBundleMessageSource messageSource = new  ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/api_error_messages");
		messageSource.setDefaultEncoding("UTF-8");
		logger.info("Leaving apiErrorMessageSource, after messageSource" +messageSource);
		return messageSource;
	}

}
