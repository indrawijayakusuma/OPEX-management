package com.bni.report.repositories;

import com.bni.report.entities.validators.ValidatorMataAnggaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ValidatorMataAnggaranRepository extends JpaRepository<ValidatorMataAnggaran, String> {
}
