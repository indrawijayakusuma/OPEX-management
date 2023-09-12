package com.bni.report.repositories;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KegiatanRepository extends JpaRepository<Kegiatan, Integer> {

    @Query("SELECT p FROM Kegiatan p WHERE p.validate = false AND p.program.beban.kelompok.id = :kelompokId")
    Page<Kegiatan> getUnValidateKegiatan(@Param("kelompokId") Integer id, Pageable pageable);

    @Query("SELECT p FROM Kegiatan p WHERE p.validate = true AND p.program.id = :programId")
    Page<Kegiatan> getAllByBebanId(@Param("programId") String id, Pageable pageable);

    Page<Kegiatan> findByProgramId(String id,Pageable pageable);

    List<Kegiatan> findByProgramId(String id);

    @Query("SELECT p FROM Kegiatan p WHERE CONCAT(p.budget) LIKE CONCAT('%',:keyword,'%') AND p.program.id = :id AND p.validate = true")
    Page<Kegiatan> search(@Param("keyword") String keyword ,@Param("id") String id, Pageable pageable);


}
