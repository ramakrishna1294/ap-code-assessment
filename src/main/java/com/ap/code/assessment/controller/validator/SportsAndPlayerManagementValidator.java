package com.ap.code.assessment.controller.validator;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import com.ap.code.assessment.controller.request.PlayerRequest;
import com.ap.code.assessment.controller.request.SportRequest;
import com.ap.code.assessment.controller.request.UpdatePlayerRequest;
import com.ap.code.assessment.exception.RequestValidationFailedException;

@Component
public class SportsAndPlayerManagementValidator implements Validator {
	
	
	public static final Logger logger = LoggerFactory.getLogger(SportsAndPlayerManagementValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return PlayerRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target instanceof PlayerRequest) {
			logger.info("Started validating PlayerRequest");
			PlayerRequest playerRequest = (PlayerRequest) target;
			validateplayerRequest(playerRequest);
		}

	}

	public void validateplayerRequest(PlayerRequest playerRequest) {

		BeanPropertyBindingResult results = new BeanPropertyBindingResult(playerRequest, "playerRequest");
		ObjectError error = null;
		if (CollectionUtils.isEmpty(playerRequest.getEmailId())) {
			logger.info("Email Ids are required, This is a Bad request");
			error= new ObjectError("emailsRequired", "ap.error.emailsRequired");
			results.addError(error);
		}
		
		if(results.hasErrors()) {
			throw new RequestValidationFailedException(results, "getSportswithAssociatedPlayers");
		}

	}
	
	
	public void validateUpdatePlayerRequest(UpdatePlayerRequest updatePlayerRequest) {

		BeanPropertyBindingResult results = new BeanPropertyBindingResult(updatePlayerRequest, "playerRequest");
		ObjectError error = null;
		if (Objects.isNull(updatePlayerRequest.getEmailId())) {
			logger.info("Email Id are required, This is a Bad request");
			error= new ObjectError("emailRequired", "ap.error.update.emailRequired");
			results.addError(error);
		}
		if (Objects.isNull(updatePlayerRequest.getSportRequest()) || CollectionUtils.isEmpty(updatePlayerRequest.getSportRequest().getSportName())) {
			logger.info("Sport Name are required, This is a Bad request");
			error= new ObjectError("emailRequired", "ap.error.update.sportRequired");
			results.addError(error);
		}
		
		if(results.hasErrors()) {
			throw new RequestValidationFailedException(results, "updatePlayerSports");
		}

	}
	
	public void validateSportsRequest(SportRequest sportRequest) {

		BeanPropertyBindingResult results = new BeanPropertyBindingResult(sportRequest, "sportRequest");
		ObjectError error = null;
		if (CollectionUtils.isEmpty(sportRequest.getSportName())) {
			logger.info("Sport Name are required, This is a Bad request");
			error= new ObjectError("sportsName", "ap.error.sportsName");
			results.addError(error);
		}
		
		if(results.hasErrors()) {
			throw new RequestValidationFailedException(results, "deleteSports");
		}

	}

}
