package com.ap.code.assessment.exception;

import org.springframework.http.HttpStatus;

public enum ApiErrorCodes implements ErrorCode{
	
	
	PLAYER_NOT_FOUND("ap.error.playerNotFould", HttpStatus.BAD_REQUEST),
	SPORT_NOT_FOUND("ap.error.sportNotFould", HttpStatus.BAD_REQUEST),
	SPORT_DUP_FOUND("ap.error.sportDuplicat", HttpStatus.BAD_REQUEST);
	
	private final String code;
	
	private final HttpStatus httpStatus;
	
	@Override
	public String code() {
		return code;
	}
	
	@Override
	public HttpStatus httpStatus() {
		
		return httpStatus;
	}
	
	ApiErrorCodes(String code, HttpStatus httpStatus){
		this.code = code;
		this.httpStatus = httpStatus;
	}

}
