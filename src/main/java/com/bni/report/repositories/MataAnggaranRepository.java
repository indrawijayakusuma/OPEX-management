package com.bni.report.repositories;

import com.bni.report.entities.MataAnggaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MataAnggaranRepository extends JpaRepository<MataAnggaran, String> {
    MataAnggaran findByNomerRekening(String nomerRekening);
    List<MataAnggaran> findByKelompokId(Integer id);
    MataAnggaran findByMataAnggaran(String mataAnggaran);
}
