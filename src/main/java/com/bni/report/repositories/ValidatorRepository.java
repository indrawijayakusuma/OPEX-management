package com.bni.report.repositories;

import com.bni.report.entities.Validator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidatorRepository extends JpaRepository<Validator, Integer> {
    Validator findByName(String name);
}
