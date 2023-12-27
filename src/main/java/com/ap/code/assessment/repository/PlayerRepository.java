package com.ap.code.assessment.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ap.code.assessment.entity.Player;

@Repository
public interface PlayerRepository  extends JpaRepository<Player, Long> {

	List<Player> findAllByEmailIn(Set<String> emailIds);
	
	@Query("select player from Player player left join player.relation relation where relation.id is null")
	List<Player> findAllWhereRelationIsNull();
	
	Player findByEmail(String emailId);
	
	Page<Player> findAll(Pageable pageable);
	
	@Query("select player from Player player left join player.relation relation"
			+ "left join relation.sport sport where sport.name =:sportNames")
	Page<Player> findAllBySportName(String sportNames, Pageable pageable);

}
