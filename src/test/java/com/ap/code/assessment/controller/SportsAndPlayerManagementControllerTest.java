package com.ap.code.assessment.controller;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ap.code.assessment.controller.validator.SportsAndPlayerManagementValidator;
import com.ap.code.assessment.service.SportsAndPlayerService;

@ExtendWith(MockitoExtension.class)
public class SportsAndPlayerManagementControllerTest {
	
	@InjectMocks
	 private  SportsAndPlayerManagementController  sportsAndPlayerManagementController;
	
	
	@Mock
	private SportsAndPlayerService sportsAndPlayerService;
	
	@Mock
	private SportsAndPlayerManagementValidator apCodeRequestValidator;
	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
