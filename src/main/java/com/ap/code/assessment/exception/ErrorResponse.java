package com.ap.code.assessment.exception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ErrorResponse {
	
	public static final Logger logger = LoggerFactory.getLogger(ErrorResponse.class);
	
	private final int statusCode;
	
	private final String reason;
	
	private final List<ApiError> errors;
	
	private ErrorResponse(int statusCode, String reason,  List<ApiError> errors) {
		this.statusCode = statusCode;
		this.reason = reason;
		this.errors = errors;
	}

	static ErrorResponse ofErrors(HttpStatus status, List<ApiError> errors) {
		return new ErrorResponse(status.value(), status.getReasonPhrase(), errors);
	}
}
