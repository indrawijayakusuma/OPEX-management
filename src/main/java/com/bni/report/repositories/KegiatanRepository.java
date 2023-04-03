package com.bni.report.repositories;

import com.bni.report.entities.Kegiatan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KegiatanRepository extends JpaRepository<Kegiatan, Integer> {
    Page<Kegiatan> findByBebanId(Integer id,Pageable pageable);
}
