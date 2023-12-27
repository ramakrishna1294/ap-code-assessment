package com.ap.code.assessment.exception;

import org.springframework.stereotype.Component;


/**
 * ApiExceptionMapper is responsible for Mapping Business exception
 * to its Corresponding errors (4** or 5**)
 * @author Ramakrishna
 */
public class ApiExceptionMapper {
	
	private ApiExceptionMapper() { super();}
	
	@Component
	static class PlayerNotAvaliableExceptionToError implements ExceptionToErrorCode {

		@Override
		public boolean canHandel(Exception exception) {
			return exception instanceof PlayerNotAvaliableException;
		}

		@Override
		public ErrorCode toErrorCode(Exception exception) {
			
			return ApiErrorCodes.PLAYER_NOT_FOUND;
		}
		
	}
	
	@Component
	static class SportNotAvaliableExceptionToError implements ExceptionToErrorCode {

		@Override
		public boolean canHandel(Exception exception) {
			return exception instanceof SportNotAvaliableException;
		}

		@Override
		public ErrorCode toErrorCode(Exception exception) {
			
			return ApiErrorCodes.SPORT_NOT_FOUND;
		}
		
	}
	
	@Component
	static class DuplicateSportExceptionToError implements ExceptionToErrorCode {

		@Override
		public boolean canHandel(Exception exception) {
			return exception instanceof DuplicateSportException;
		}

		@Override
		public ErrorCode toErrorCode(Exception exception) {
			
			return ApiErrorCodes.SPORT_DUP_FOUND;
		}
		
	}
	
	@Component
	static class ConnectivityExceptionToError implements ExceptionToErrorCode {

		@Override
		public boolean canHandel(Exception exception) {
			return exception instanceof ConnectivityException;
		}

		@Override
		public ErrorCode toErrorCode(Exception exception) {
			
			return ApiErrorCodes.DB_ERROR;
		}
		
	}
	
	@Component
	static class SystemExceptionToError implements ExceptionToErrorCode {

		@Override
		public boolean canHandel(Exception exception) {
			return exception instanceof SystemException;
		}

		@Override
		public ErrorCode toErrorCode(Exception exception) {
			
			return ApiErrorCodes.SYSTEM_ERROR;
		}
		
	}

}
