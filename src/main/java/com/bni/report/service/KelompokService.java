package com.bni.report.service;

import com.bni.report.entities.Kelompok;
import com.bni.report.repositories.KelompokRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KelompokService {

    @Autowired
    private KelompokRepository kelompokRepository;

    public List<Kelompok> getALl() {
        return kelompokRepository.findAll();
    }

    public Kelompok findById(Integer id) {
        return kelompokRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Kelompok create(Kelompok kelompok) {
        return kelompokRepository.save(kelompok);
    }

    public Kelompok save(Kelompok kelompok) {
        return kelompokRepository.save(kelompok);
    }

}
