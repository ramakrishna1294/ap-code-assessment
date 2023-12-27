package com.ap.code.assessment.exception;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 * This class Acts As a resolver for {@linkplain ExceptionToErrorCode} implementation 
 * when calling the {@linkplain #of(Exception)} method, client can find the correct error code for the given
 * {@linkplain Exception}.
 * 
 * @param apContext
 */
@Component
public class ErrorCodeResolver {
	
	public static final Logger logger = LoggerFactory.getLogger(ErrorCodeResolver.class);
	
	private final ApplicationContext apContext;
	
	
	public ErrorCodeResolver(ApplicationContext apContext) {
		this.apContext = apContext;
	}
	
	public  ErrorCode of(Exception exception) {
		logger.info("Finding the ExceptionToErrorCode implementation for " + exception.toString());
		
		return implementations().filter(impl -> impl.canHandel(exception)).findFirst()
				.map(impl -> impl.toErrorCode(exception)).orElse(null);
	}
	
	
	private Stream<ExceptionToErrorCode> implementations() {
		return apContext.getBeansOfType(ExceptionToErrorCode.class).values().stream();
	}
	
}
