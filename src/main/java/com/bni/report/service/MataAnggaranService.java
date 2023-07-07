package com.bni.report.service;

import com.bni.report.entities.MataAnggaran;
import com.bni.report.repositories.MataAnggaranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MataAnggaranService {
    @Autowired
    private MataAnggaranRepository mataAnggaranRepository;

    public MataAnggaran create(MataAnggaran mataAnggaran) {
        return mataAnggaranRepository.save(mataAnggaran);
    }

    public List<MataAnggaran> getAll(Integer id) {
        return mataAnggaranRepository.findByKelompokId(id);
    }

    public String getNomerRekening(String mataAnggaran) {
        return mataAnggaranRepository.findByMataAnggaran(mataAnggaran).getNomerRekening();
    }
}
