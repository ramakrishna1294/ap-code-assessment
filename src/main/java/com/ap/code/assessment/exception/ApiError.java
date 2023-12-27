package com.ap.code.assessment.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ApiError {
	private static final long serialVersionUID = -21312312312L;
	
	private final String code;
	
	private final String message;
	
	public ApiError(String code, String message) {
		
		this.code=code;
		this.message=message;
	}

}
