package com.ap.code.assessment.controller.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePlayerRequest {
	
	private String emailId;
	private SportRequest sportRequest;
}
