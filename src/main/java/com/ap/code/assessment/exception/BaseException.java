package com.ap.code.assessment.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseException  extends RuntimeException{

	private static final long serialVersionUID = -13123123123123213L;
	
	private String serviceSource = "getSportswithAssociatedPlayers";
	
	private HttpStatus httpStatus;
	
	private List<Error> errors;
	
	public BaseException(String message) {
		super(message);
	}
	
	
}
