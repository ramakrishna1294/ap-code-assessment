package com.ap.code.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ap.code.assessment.entity.Relation;

@Repository
public interface RelationsRepository  extends JpaRepository<Relation, Long> {

}
