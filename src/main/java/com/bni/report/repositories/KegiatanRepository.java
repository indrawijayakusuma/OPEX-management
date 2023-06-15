package com.bni.report.repositories;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KegiatanRepository extends JpaRepository<Kegiatan, Integer> {
    Page<Kegiatan> findByProgramId(String id,Pageable pageable);

    List<Kegiatan> findByProgramId(String id);

    @Query("SELECT p FROM Kegiatan p WHERE CONCAT(p.budget) LIKE CONCAT('%',:keyword,'%') AND p.program.id = :id")
    Page<Kegiatan> search(@Param("keyword") String keyword ,@Param("id") String id, Pageable pageable);

}
