package com.bni.report.repositories;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProgramRepository extends JpaRepository<Program, String> {
    Page<Program> findByBebanId(Integer id, Pageable pageable);
    @Query("SELECT p FROM Program p WHERE CONCAT(p.name, p.pic, p.budget) LIKE CONCAT('%',:keyword,'%') AND p.beban.id = :id")
    Page<Program> search(@Param("keyword") String keyword , @Param("id") Integer id, Pageable pageable);
}
