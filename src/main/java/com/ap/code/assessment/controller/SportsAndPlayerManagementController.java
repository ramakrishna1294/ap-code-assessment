package com.ap.code.assessment.controller;



import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ap.code.assessment.controller.request.PlayerRequest;
import com.ap.code.assessment.controller.request.SportRequest;
import com.ap.code.assessment.controller.request.UpdatePlayerRequest;
import com.ap.code.assessment.controller.response.PlayerResponse;
import com.ap.code.assessment.controller.validator.SportsAndPlayerManagementValidator;
import com.ap.code.assessment.service.SportsAndPlayerService;



@RestController
public class SportsAndPlayerManagementController {
	public static final Logger logger = LoggerFactory.getLogger(SportsAndPlayerManagementController.class);
	
	@Autowired
	private SportsAndPlayerService sportsAndPlayerService;
	
	@Autowired
	private SportsAndPlayerManagementValidator apCodeRequestValidator;
	
	
	/**
	 * This API is to to Get all the Sport Associated with the player
	 * @param entity holds the list of all the email id of the players
	 * @return list of players and Associated sports
	 * 
	 */
	@PostMapping("/getSportswithAssociatedPlayers")
	public List<PlayerResponse> getSportswithAssociatedPlayers( @RequestBody PlayerRequest entity) {
		logger.info("Entered getSportswithAssociatedPlayers");
		
		apCodeRequestValidator.validateplayerRequest(entity);
		List<PlayerResponse> response = sportsAndPlayerService.getSportswithAssociatedPlayers(entity.getEmailId());
		logger.info("Complted getSportswithAssociatedPlayers");
		return  response;
	}
	
	
	/**
	 * This Api is to Get all the Players Associated to No Game 
	 * @return list of players who are not Associated to any sports
	 */
	@GetMapping("/getPlayersWithNoSports")
	public List<PlayerResponse> getPlayersWithNoSports() {
		logger.info("Entered getPlayersWithNoSports");
		List<PlayerResponse> response =  sportsAndPlayerService.getPlayersWithNoSports();
		logger.info("Complted getPlayersWithNoSports");
		return  response;
	}
	
	
	/**
	 * This is a Update Api, This Can be used to Associated a player to one or more games.
	 * @param entity Player email id and the Sports he is Associated with
	 * @return Updated player info and Associated sports
	 */
	@PutMapping("/updatePlayerSports")
	public PlayerResponse updatePlayerSports( @RequestBody UpdatePlayerRequest entity) {
		logger.info("Entered updatePlayerSports");
		apCodeRequestValidator.validateUpdatePlayerRequest(entity);
		PlayerResponse response = sportsAndPlayerService.updatePlayer(entity);
		logger.info("Complted updatePlayerSports");
		return  response;
	}
	
	
	/**
	 * This is a Delete Api Use for deleting any Sport and Mapping to the Player
	 * Note the player will not be deleted only the Associated Sport info and the Sport will be deleted
	 * @param entity
	 * @return 
	 */
	@DeleteMapping("/deleteSports")
	public String deleteSports( @RequestBody SportRequest entity) {
		logger.info("Entered getSportswithAssociatedPlayers");
		
		apCodeRequestValidator.validateSportsRequest(entity);
		
		logger.info("Complted getSportswithAssociatedPlayers");
		sportsAndPlayerService.deletePlayer(entity.getSportName());
		  
		return "Has been deleted sucessfully";
	}
	
	
	/**
	 * This is Paginated Api to get all the player or players Associated to a Sport 
	 * THis Api will provide the pagination information 
	 * @param sportName
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/getPlayerListwithSportAndPaginated")
	public  Map<String, Object> getPlayerListwithSportAndPaginated( 
			@RequestParam(required = false) String sportName,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size) {
		logger.info("Entered getPlayerListwithSportAndPaginated");
		
		Pageable paging = PageRequest.of(page, size);
		Map<String, Object> playerResponse = sportsAndPlayerService.getPlayerListWithPagination(paging, sportName);
		logger.info("Complted getPlayerListwithSportAndPaginated");
		return  playerResponse;
	}
	
}
