package com.ap.code.assessment.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import com.ap.code.assessment.controller.request.PlayerRequest;
import com.ap.code.assessment.controller.request.SportRequest;
import com.ap.code.assessment.controller.request.UpdatePlayerRequest;
import com.ap.code.assessment.controller.response.PlayerResponse;
import com.ap.code.assessment.controller.validator.SportsAndPlayerManagementValidator;
import com.ap.code.assessment.exception.ConnectivityException;
import com.ap.code.assessment.exception.DuplicateSportException;
import com.ap.code.assessment.exception.SportNotAvaliableException;
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
		doNothing().when(apCodeRequestValidator).validateplayerRequest(Mockito.any());
	}

	
	//********* getSportswithAssociatedPlayers STARTED ************/
	@Test
	public void getSportswithAssociatedPlayers_HappyPath() {
		when(sportsAndPlayerService.getSportswithAssociatedPlayers(anyList())).thenReturn(buildListPlayerResponse(true));
		List<PlayerResponse> response = sportsAndPlayerManagementController.getSportswithAssociatedPlayers(PlayerRequest.builder().build());
		assertNotNull(response);
		assertFalse(CollectionUtils.isEmpty(response));
		assertNotNull(response.get(0).getSports());
	}
	
	@Test
	public void getSportswithAssociatedPlayers_HappyPathNullResponse() {
		when(sportsAndPlayerService.getSportswithAssociatedPlayers(anyList())).thenReturn(null);
		List response = sportsAndPlayerManagementController.getSportswithAssociatedPlayers(PlayerRequest.builder().build());
		assertNull(response);
	}
	
	
	@Test(expected = ConnectivityException.class)
	public void getSportswithAssociatedPlayers_ConnectivityException() {
		
		when(sportsAndPlayerService.getSportswithAssociatedPlayers(anyList())).thenThrow(ConnectivityException.class);
		List response = sportsAndPlayerManagementController.getSportswithAssociatedPlayers(PlayerRequest.builder().build());
	}
	
	//********* getSportswithAssociatedPlayers ENDED ************/
	
	
	
	//********   getPlayersWithNoSports Started ****************/
	
	@Test
	public void getPlayersWithNoSports_HappyPathResponse() {
		when(sportsAndPlayerService.getPlayersWithNoSports()).thenReturn(buildListPlayerResponse(false));
		List response = sportsAndPlayerManagementController.getPlayersWithNoSports();
		assertNull(response);
	}
	
	@Test
	public void getPlayersWithNoSports_HappyPathNullResponse() {
		when(sportsAndPlayerService.getPlayersWithNoSports()).thenReturn(null);
		List response = sportsAndPlayerManagementController.getPlayersWithNoSports();
		assertNull(response);
	}
	
	@Test(expected = ConnectivityException.class)
	public void getPlayersWithNoSports_ConnectivityException() {
		
		when(sportsAndPlayerService.getSportswithAssociatedPlayers(anyList())).thenThrow(ConnectivityException.class);
		List response = sportsAndPlayerManagementController.getPlayersWithNoSports();
	}
	
	//********   getPlayersWithNoSports ended ****************/
	
	
	
	//********   updatePlayerSports Started ****************/
	
		@Test
		public void updatePlayer_HappyPathResponse() {
			doNothing().when(apCodeRequestValidator).validateUpdatePlayerRequest(Mockito.any());
			when(sportsAndPlayerService.getPlayersWithNoSports()).thenReturn(buildListPlayerResponse(false));
			PlayerResponse response = sportsAndPlayerManagementController.updatePlayerSports(UpdatePlayerRequest.builder().build());
			assertNotNull(response);
		}
		
		@Test(expected = DuplicateSportException.class)
		public void updatePlayer_HappyPathNullResponse() {
			doNothing().when(apCodeRequestValidator).validateUpdatePlayerRequest(Mockito.any());
			when(sportsAndPlayerService.updatePlayer(any())).thenThrow(DuplicateSportException.class);
			PlayerResponse response = sportsAndPlayerManagementController.updatePlayerSports(UpdatePlayerRequest.builder().build());
		}
		
		@Test(expected = ConnectivityException.class)
		public void updatePlayer_ConnectivityException() {
			doNothing().when(apCodeRequestValidator).validateUpdatePlayerRequest(Mockito.any());
			when(sportsAndPlayerService.updatePlayer(any())).thenThrow(ConnectivityException.class);
			PlayerResponse response = sportsAndPlayerManagementController.updatePlayerSports(UpdatePlayerRequest.builder().build());
		}
		
	//********   updatePlayerSports ended ****************/
	
	
	//********   deleteSports Started ****************/
	
		@Test
		public void deleteSports_HappyPathResponse() {
			doNothing().when(apCodeRequestValidator).validateSportsRequest(Mockito.any());
			when(sportsAndPlayerService.deletePlayer(anyList())).thenReturn(true);
			String response = sportsAndPlayerManagementController.deleteSports(SportRequest.builder().build());
			assertNotNull(response);
		}
		
		@Test(expected = ConnectivityException.class)
		public void deleteSports_ConnectivityException() {
			doNothing().when(apCodeRequestValidator).validateSportsRequest(Mockito.any());
			when(sportsAndPlayerService.deletePlayer(anyList())).thenThrow(ConnectivityException.class);
			String response = sportsAndPlayerManagementController.deleteSports(SportRequest.builder().build());
		}
		
		@Test(expected = SportNotAvaliableException.class)
		public void deleteSports_SportNotAvaliableException() {
			doNothing().when(apCodeRequestValidator).validateSportsRequest(Mockito.any());
			when(sportsAndPlayerService.deletePlayer(anyList())).thenThrow(SportNotAvaliableException.class);
			String response = sportsAndPlayerManagementController.deleteSports(SportRequest.builder().build());
		}
		
		//********   deleteSports ended ****************/
		
		
		
		//********   getPlayerListwithSportAndPaginated Started ****************/
		
			@Test
			public void getPlayerListwithSportAndPaginated_HappyPathResponse() {
				doNothing().when(apCodeRequestValidator).validateSportsRequest(Mockito.any());
				when(sportsAndPlayerService.getPlayerListWithPagination(any(), anyString())).thenReturn(null);
				Map<String, Object> playerResponse = sportsAndPlayerManagementController.getPlayerListwithSportAndPaginated("Test",10,7);
				assertNull(playerResponse);
			}
			
			@Test(expected = ConnectivityException.class)
			public void getPlayerListwithSportAndPaginated_ConnectivityException() {
				doNothing().when(apCodeRequestValidator).validateSportsRequest(Mockito.any());
				when(sportsAndPlayerService.deletePlayer(anyList())).thenThrow(ConnectivityException.class);
				Map<String, Object> playerResponse = sportsAndPlayerManagementController.getPlayerListwithSportAndPaginated("Test",10,7);
			}
			
			
			//********   getPlayerListwithSportAndPaginated ended ****************/
	
	
	
	private List<PlayerResponse> buildListPlayerResponse(boolean withSports){
		return withSports? Collections.singletonList(buildPlayerResponseWithSports()):  Collections.singletonList(buildPlayerResponseWithOutSports());
	}
	
	private PlayerResponse buildPlayerResponseWithSports() {
		List<String> sports = new ArrayList<>();
		sports.add("NFL");
		return PlayerResponse.builder()
							 .age(10)
							 .email("SAMPLE@GMAIL.COM")
							 .gender("MALE")
							 .sports(sports)
							 .build();
	}
	
	private PlayerResponse buildPlayerResponseWithOutSports() {
		return PlayerResponse.builder()
							 .age(10)
							 .email("SAMPLE@GMAIL.COM")
							 .gender("MALE")
							 .sports(null)
							 .build();
	}

}
