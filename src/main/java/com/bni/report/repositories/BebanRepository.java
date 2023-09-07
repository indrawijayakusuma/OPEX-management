package com.bni.report.repositories;

import com.bni.report.entities.Beban;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BebanRepository extends JpaRepository<Beban, Integer> {
    @Query("SELECT p FROM Beban p WHERE CONCAT(p.name, p.budget, p.sisa, p.date) LIKE CONCAT('%',:keyword,'%') AND p.kelompok.id = :kelompokId AND p.validate = true")
    Page<Beban> search(@Param("keyword") String keyword, Pageable pageable, @Param("kelompokId") Integer id);

//    Page<Beban>findByKelompokId(Integer id, Pageable pageable);
    @Query("SELECT p FROM Beban p WHERE p.validate = true AND p.kelompok.id = :kelompokId")
    Page<Beban> getValidateBeban(@Param("kelompokId") Integer id, Pageable pageable);

    @Query("SELECT p FROM Beban p WHERE p.validate = false AND p.kelompok.id = :kelompokId")
    Page<Beban> getUnValidateBeban(@Param("kelompokId") Integer id, Pageable pageable);

    Beban findByName(String mataAnggaran);

    List<Beban> findByKelompokId(Integer id);
}
