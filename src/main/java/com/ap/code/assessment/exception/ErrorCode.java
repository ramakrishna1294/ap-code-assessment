package com.ap.code.assessment.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

	String code();
	
	HttpStatus httpStatus();
	
}
