package com.bni.report.repositories;

import com.bni.report.entities.Beban;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface BebanRepository extends JpaRepository<Beban, Integer> {
    @Query("SELECT p FROM Beban p WHERE CONCAT(p.name, p.budget, p.sisa, p.date) LIKE %?1%")
    Page<Beban> search(String keyword, Pageable pageable);
//    Page<Beban> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
