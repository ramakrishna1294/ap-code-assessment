package com.ap.code.assessment.repository;

import java.util.List;
import java.util.Set;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ap.code.assessment.entity.Sport;

@Repository
public interface SportRepository  extends CrudRepository<Sport, Long> {

	List<Sport> findAllByNameIn(Set<String> names);
}
