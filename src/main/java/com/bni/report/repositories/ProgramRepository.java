package com.bni.report.repositories;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProgramRepository extends JpaRepository<Program, String> {
    @Query("SELECT p FROM Program p WHERE p.validate = false AND p.beban.kelompok.id = :kelompokId")
    Page<Program> getUnValidateBeban(@Param("kelompokId") Integer id, Pageable pageable);

    @Query("SELECT p FROM Program p WHERE p.validate = true AND p.beban.id = :bebanId")
    Page<Program> getAllByBebanId(@Param("bebanId") Integer id, Pageable pageable);

    @Query("SELECT p FROM Program p WHERE CONCAT(p.name, p.pic, p.budget) LIKE CONCAT('%',:keyword,'%') AND p.beban.id = :id AND p.validate = true")
    Page<Program> search(@Param("keyword") String keyword, @Param("id") Integer id, Pageable pageable);
}
