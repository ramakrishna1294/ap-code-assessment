package com.ap.code.assessment.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ap.code.assessment.controller.request.UpdatePlayerRequest;
import com.ap.code.assessment.controller.response.PlayerResponse;
import com.ap.code.assessment.convertor.PlayerResponseConverter;
import com.ap.code.assessment.entity.Player;
import com.ap.code.assessment.entity.Relation;
import com.ap.code.assessment.entity.Sport;
import com.ap.code.assessment.exception.ConnectivityException;
import com.ap.code.assessment.exception.DuplicateSportException;
import com.ap.code.assessment.exception.PlayerNotAvaliableException;
import com.ap.code.assessment.exception.SportNotAvaliableException;
import com.ap.code.assessment.exception.SystemException;
import com.ap.code.assessment.repository.PlayerRepository;
import com.ap.code.assessment.repository.RelationsRepository;
import com.ap.code.assessment.repository.SportRepository;


/**
 * This is a Service Class Handling all the business logic
 * 
 * @author ramakrishna
 */
@Service
public class SportsAndPlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private SportRepository sportRepository;
	
	@Autowired
	private RelationsRepository relationsRepository;
	
	@Autowired
	private PlayerResponseConverter playerResponseConverter; 
	
	
	/**
	 * This method is to get all the Sports Associated with the player  
	 * @param emailId
	 * @return 
	 */
	public List<PlayerResponse> getSportswithAssociatedPlayers(List<String> emailId) {
		List<Player> players = null;
		try {
			players = playerRepository.findAllByEmailIn(new HashSet<String>(emailId));
			if(CollectionUtils.isEmpty(players)) {
				 throw new PlayerNotAvaliableException();
			}
		}catch(DataAccessException dataAccessException) {
			throw new ConnectivityException();
		}catch (Exception exception) {
			throw new SystemException();
		}
		return players.stream().map(player -> playerResponseConverter.convert(player)).collect(Collectors.toList());
		
	}
	
	
	public List<PlayerResponse> getPlayersWithNoSports() {
		List<Player> players = null;
		try {
			players = playerRepository.findAllWhereRelationIsNull();
		}catch(DataAccessException dataAccessException) {
			throw new ConnectivityException();
		}catch (Exception exception) {
			throw new SystemException();
		}
		return players.stream().map(player -> playerResponseConverter.convert(player)).collect(Collectors.toList());
	}
	
	
	public PlayerResponse updatePlayer(UpdatePlayerRequest request) {
		try {
			Player player = playerRepository.findByEmail(request.getEmailId());
			
			List<Sport> sports = sportRepository.findAllByNameIn(new HashSet<String>(request.getSportRequest().getSportName()));
			List<Relation> updatedRelation = null;
			if(!CollectionUtils.isEmpty(player.getRelation())) {
				updatedRelation = new ArrayList<Relation>();
				if(player.getRelation().stream().anyMatch(relative -> request.getSportRequest().getSportName().contains(relative.getSport().getName()))) {
					throw new DuplicateSportException();
				}
			}	
			updatedRelation = new ArrayList<Relation>();
			updatedRelation.addAll(sports.stream().map(sport -> populateRelation(player, sport)).collect(Collectors.toList()));
			relationsRepository.saveAll(updatedRelation);
			if(CollectionUtils.isEmpty(player.getRelation())){
				player.setRelation(updatedRelation);
			}else {
				player.getRelation().addAll(updatedRelation);
			}
			return playerResponseConverter.convert(player);
		}catch(DataAccessException dataAccessException) {
			throw new ConnectivityException();
		}
	}
	
	private Relation populateRelation(Player player, Sport sport) {
		Relation relation = new Relation();
		Player playerRefrence = new Player();
		playerRefrence.setId(player.getId());
		relation.setSport(sport);
		relation.setPlayer(playerRefrence);
		return relation;
	}
	
	
	public boolean deletePlayer(List<String> sportNames) {
		try {
			List<Sport> sportDetails = sportRepository.findAllByNameIn(new HashSet<String>(sportNames));
			if(CollectionUtils.isEmpty(sportDetails)) {
				 throw new SportNotAvaliableException();
			}
			sportRepository.deleteAll(new HashSet<Sport>(sportDetails));
			return false;
		}catch(DataAccessException dataAccessException) {
			throw new ConnectivityException();
		}catch (Exception exception) {
			throw new SystemException();
		}
	}
	
	public Map<String, Object>  getPlayerListWithPagination(Pageable paging, String sportName){
		Page<Player> players = null;
		try {
			if(sportName != null) {
				players = playerRepository.findAllBySportName(sportName, paging);
			}else {
				players = playerRepository.findAll(paging);
			}
			if(CollectionUtils.isEmpty(players.getContent())) {
				 throw new PlayerNotAvaliableException();
			}
		}catch(DataAccessException dataAccessException) {
			throw new ConnectivityException();
		}catch (Exception exception) {
			throw new SystemException();
		}
		
		List<PlayerResponse> playerResponseList =players.getContent().stream().map(player -> playerResponseConverter.convert(player)).collect(Collectors.toList());
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("context", playerResponseList);
		response.put("currentPage", players.getNumber());
      	response.put("totalItems", players.getTotalElements());
      	response.put("totalPages", players.getTotalPages());

      	return response;
	}
}
