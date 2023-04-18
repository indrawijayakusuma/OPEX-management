package com.bni.report.repositories;

import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.Kelompok;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KelompokRepository extends JpaRepository<Kelompok, Integer> {
}
