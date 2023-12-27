package com.ap.code.assessment.exception;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletResponse;
/**
 * Exception Handler that catches all the exceptions thrown by the 
 * rest layer and converts them to appropriate {@linkplain ErrorResponse} with suitable status Code
 * 
 * @author Ramakrishna
 */
@ControllerAdvice
public class ApiExceptionHandler  extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource apiErrorMessageSource;
	
	@Autowired
	private ErrorCodeResolver errorCodeResolver;
	
	@ExceptionHandler({BaseException.class, CompletionException.class})
    ResponseEntity<Object> handleServiceException(BaseException exception, Locale locale) throws IOException   {
        
		ErrorCode errorCode = errorCodeResolver.of(exception);
		ResponseEntity<ErrorResponse> responseEntity = null;
		ErrorResponse errorResponse = null;
		if(exception instanceof RequestValidationFailedException) {
			responseEntity = handleRequestValidationFailedException(exception, locale);
		}else {
			errorResponse = ErrorResponse.ofErrors(errorCode.httpStatus(), Collections.singletonList(toApiError(errorCode, locale)));
			responseEntity = ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
		}
		return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
	
	
	@ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException   {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
	
	
	private ResponseEntity<ErrorResponse> handleRequestValidationFailedException(BaseException exception, Locale locale){
		Stream<ObjectError> errors = ((RequestValidationFailedException)exception).getBindingResults().getAllErrors().stream();
		List<ApiError> apiErrors = errors.map(ObjectError::getDefaultMessage)
										.map(this::validateErrorCode)
										.map(code -> toApiError(code, locale)).collect(Collectors.toList());
		
		return ResponseEntity.badRequest().body(ErrorResponse.ofErrors(HttpStatus.BAD_REQUEST, apiErrors));
	}
	
	private ErrorCode validateErrorCode(final String errorCode) {
		return new ErrorCode() {
			
			@Override
			public HttpStatus httpStatus() {
				
				return HttpStatus.BAD_REQUEST;
			}
			
			@Override
			public String code() {
				return errorCode;
			}
		};
	}
	
	private ApiError toApiError(ErrorCode errorCode, Locale locale) {
		
		 Pair<String, String>  pair = toKeyValuePairOfError(errorCode.code(), new Object[] {});
		return new ApiError(pair.getFirst(), pair.getSecond());
	}
	
	private Pair<String, String> toKeyValuePairOfError(String errorId, Object[] object){
		String message = "";
		String exceptionCode = "";
		
		try {
			String[] errorMessage = apiErrorMessageSource.getMessage(errorId, object, Locale.US).split(":");
			
			if(errorMessage.length >= 2) {
				exceptionCode = errorMessage[0];
				message = errorMessage[1];
			}else {
				exceptionCode = errorId;
				message = errorMessage[1];
			}
			
		}catch (NoSuchMessageException e) {
			exceptionCode = errorId;
			message = "Message is missing";
		}
		
		return Pair.of(exceptionCode, message);
		
	}
	
	
	
}
