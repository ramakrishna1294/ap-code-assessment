package com.ap.code.assessment.controller.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerResponse {
	
	
	private String email;
	private int level;
	private int age;
	private String gender;
	private List<String> sports;
	
	

}
