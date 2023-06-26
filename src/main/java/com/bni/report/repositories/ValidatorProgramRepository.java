package com.bni.report.repositories;

import com.bni.report.entities.validators.ValidatorProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidatorProgramRepository extends JpaRepository<ValidatorProgram, String> {
}
