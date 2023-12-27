package com.ap.code.assessment.exception;

import org.springframework.validation.BeanPropertyBindingResult;

import lombok.Getter;

public class RequestValidationFailedException  extends BaseException{

	private static final long serialVersionUID = -21312312312L;
	
	@Getter
	private final BeanPropertyBindingResult bindingResults;
	

	public RequestValidationFailedException(BeanPropertyBindingResult bindingResults) {
		this.bindingResults = bindingResults;
	}
	
	public RequestValidationFailedException(BeanPropertyBindingResult bindingResults, String serviceSource) {
		this.bindingResults = bindingResults;
		super.setServiceSource(serviceSource);
	}
}
