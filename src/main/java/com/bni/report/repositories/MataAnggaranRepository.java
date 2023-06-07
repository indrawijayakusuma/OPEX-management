package com.bni.report.repositories;

import com.bni.report.entities.MataAnggaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MataAnggaranRepository extends JpaRepository<MataAnggaran, String> {
    MataAnggaran findByNomerRekening(String nomerRekening);
}
