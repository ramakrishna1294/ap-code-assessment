package com.ap.code.assessment.exception;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

public interface ExceptionToErrorCode {

	
	boolean canHandel(Exception exception);
	
	ErrorCode toErrorCode(Exception exception);
	
	default <T extends BaseException> ErrorResponse toErrorResponse(T exception) {
		HttpStatus httpStatus = (exception.getHttpStatus() != null) ? exception.getHttpStatus() : 
			toErrorCode(exception).httpStatus();
		return ErrorResponse.builder()
				.statusCode(httpStatus.value())
				.reason(httpStatus.getReasonPhrase())
				.errors(convertToApiError(exception.getErrors())).build();
	}
	
	default List<ApiError> convertToApiError(List<Error> errors){
		return (CollectionUtils.isEmpty(errors))? Collections.emptyList() :
			errors.stream().map(error -> ApiError.builder().code(error.getReasonCode()).message(error.getDescription()).build()).toList();
	}
	
	
}
