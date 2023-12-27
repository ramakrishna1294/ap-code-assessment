package com.ap.code.assessment.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import com.ap.code.assessment.controller.request.UpdatePlayerRequest;
import com.ap.code.assessment.controller.response.PlayerResponse;
import com.ap.code.assessment.convertor.PlayerResponseConverter;
import com.ap.code.assessment.entity.Player;
import com.ap.code.assessment.entity.Sport;
import com.ap.code.assessment.exception.PlayerNotAvaliableException;
import com.ap.code.assessment.exception.SportNotAvaliableException;
import com.ap.code.assessment.repository.PlayerRepository;
import com.ap.code.assessment.repository.RelationsRepository;
import com.ap.code.assessment.repository.SportRepository;


@ExtendWith(MockitoExtension.class)
public class SportsAndPlayerServiceTest {

	@InjectMocks
	private SportsAndPlayerService sportsAndPlayerService;
	
	@Mock
	private PlayerRepository playerRepository;
	
	@Mock
	private SportRepository sportRepository;
	
	@Mock
	private RelationsRepository relationsRepository;
	
	@Mock
	private PlayerResponseConverter playerResponseConverter; 
	
	
	@Before
	public void setUp() throws Exception {
	}

	/*********************getSportswithAssociatedPlayers Started ********/
	@Test
	public void getSportswithAssociatedPlayers_HappyPath() {
		when(playerRepository.findAllByEmailIn(any())).thenReturn(Collections.singletonList(buildPlayer()));
		when(playerResponseConverter.convert(any())).thenReturn(buildPlayerResponseWithSports());
		List<PlayerResponse> response =  sportsAndPlayerService.getSportswithAssociatedPlayers(Collections.singletonList("rama@test.com"));
		assertNotNull(response);
	}
	
	@Test(expected = PlayerNotAvaliableException.class)
	public void getSportswithAssociatedPlayers_PlayerNotAvaliableException() {
		when(playerRepository.findAllByEmailIn(any())).thenReturn(null);
		List<PlayerResponse> response =  sportsAndPlayerService.getSportswithAssociatedPlayers(Collections.singletonList("rama@test.com"));
		
	}
	
	@Test(expected = DataAccessException.class)
	public void getSportswithAssociatedPlayers_DataAccessException() {
		when(playerRepository.findAllByEmailIn(any())).thenThrow(DataAccessException.class);
		List<PlayerResponse> response =  sportsAndPlayerService.getSportswithAssociatedPlayers(Collections.singletonList("rama@test.com"));
		
	}
	/*********************getSportswithAssociatedPlayers ended ********/

	
	/*********************getPlayersWithNoSports  Started ***************/
	
	

	@Test
	public void getPlayersWithNoSports_HappyPath() {
		when(playerRepository.findAllWhereRelationIsNull()).thenReturn(Collections.singletonList(buildPlayer()));
		when(playerResponseConverter.convert(any())).thenReturn(buildPlayerResponseWithSports());
		List<PlayerResponse> response =  sportsAndPlayerService.getPlayersWithNoSports();
		assertNotNull(response);
	}
	
	@Test(expected = PlayerNotAvaliableException.class)
	public void etPlayersWithNoSports_PlayerNotAvaliableException() {
		when(playerRepository.findAllWhereRelationIsNull()).thenReturn(null);
		List<PlayerResponse> response =  sportsAndPlayerService.getPlayersWithNoSports();
		
	}
	
	@Test(expected = DataAccessException.class)
	public void getPlayersWithNoSports_DataAccessException() {
		when(playerRepository.findAllWhereRelationIsNull()).thenThrow(DataAccessException.class);
		List<PlayerResponse> response =  sportsAndPlayerService.getPlayersWithNoSports();
	}
	
	/*********************getPlayersWithNoSports  ended ***************/

	/*********************UpdatePlayer  Started ***************/
	
	

	@Test
	public void updatePlayer_HappyPath() {
		when(playerRepository.findByEmail(anyString())).thenReturn(buildPlayer());
		when(sportRepository.findAllByNameIn(anySet())).thenReturn(Collections.singletonList(buildSport()));
		when(playerResponseConverter.convert(any())).thenReturn(buildPlayerResponseWithSports());
		PlayerResponse response =  sportsAndPlayerService.updatePlayer(UpdatePlayerRequest.builder().build());
		assertNotNull(response);
	}
	
	@Test(expected = SportNotAvaliableException.class)
	public void updatePlayer_SportNotAvaliableException() {
		when(playerRepository.findByEmail(anyString())).thenReturn(buildPlayer());
		when(sportRepository.findAllByNameIn(anySet())).thenReturn(null);
		when(playerResponseConverter.convert(any())).thenReturn(buildPlayerResponseWithSports());
		PlayerResponse response =  sportsAndPlayerService.updatePlayer(UpdatePlayerRequest.builder().build());
	}
	
	@Test(expected = PlayerNotAvaliableException.class)
	public void updatePlayer_PlayerNotAvaliableException() {
		when(playerRepository.findByEmail(anyString())).thenReturn(null);
		PlayerResponse response =  sportsAndPlayerService.updatePlayer(UpdatePlayerRequest.builder().build());
	}
	
	@Test(expected = DataAccessException.class)
	public void updatePlayer_DataAccessException() {
		when(playerRepository.findByEmail(anyString())).thenThrow(DataAccessException.class);
		PlayerResponse response =  sportsAndPlayerService.updatePlayer(UpdatePlayerRequest.builder().build());
	}
	
	/*********************UpdatePlayer  Ended ***************/

	/*********************DeletePlayer  Started ***************/
	
	@Test
	public void DeletePlayer_happyPath() {
		when(sportRepository.findAllByNameIn(anySet())).thenReturn(Collections.singletonList(buildSport()));
		doNothing().when(sportRepository).deleteAll(anySet());
		boolean response =  sportsAndPlayerService.deletePlayer(Collections.singletonList("NFL"));
	}
	
	@Test(expected = SportNotAvaliableException.class)
	public void DeletePlayer_SportNotAvaliableException() {
		when(sportRepository.findAllByNameIn(anySet())).thenReturn(null);
		boolean response =  sportsAndPlayerService.deletePlayer(Collections.singletonList("NFL"));
	}
	
	@Test(expected = DataAccessException.class)
	public void deletePlayer_DataAccessException() {
		when(sportRepository.findAllByNameIn(anySet())).thenThrow(DataAccessException.class);
		PlayerResponse response =  sportsAndPlayerService.updatePlayer(UpdatePlayerRequest.builder().build());
	}

	/*********************DeletePlayer  Started ***************/
	
	
	
	private Player buildPlayer() {
		Player player = new Player();
		player.setAge(8);
		player.setEmail("ram@test.com");
		player.setGender("Male");
		player.setLevel(8);
		player.setId(10l);
		return player;
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
	
	private Sport buildSport() {
		Sport sport = new Sport();
		sport.setName("NFL");
		sport.setId(10l);
		return sport;
	}

}
