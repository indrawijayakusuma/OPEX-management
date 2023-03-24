package com.bni.report.service;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.repositories.BebanRepository;
import com.bni.report.repositories.KegiatanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component @Slf4j
public class KegiatanService {

    @Autowired
    private KegiatanRepository kegiatanRepository;

    public List<Kegiatan> getAll(){
        return kegiatanRepository.findAll();
    }
    public Kegiatan findById(Integer id){
        return kegiatanRepository.findById(id).orElseThrow(RuntimeException::new);
    }
    public Kegiatan create(Kegiatan kegiatan){
        return kegiatanRepository.save(kegiatan);
    }
    public Kegiatan edit (Kegiatan kegiatan){
        Kegiatan ObjectKegiatan = findById(kegiatan.getId());
        return kegiatanRepository.save(kegiatan);
    }
    public void delete(Integer id){
        kegiatanRepository.deleteById(id);
    }

    public List<Kegiatan> findByBebanId(Integer id){
        return kegiatanRepository.findByBebanId(id);
    }

    public BigDecimal addNominalKegiatan(Integer id){
        List<Kegiatan> all = kegiatanRepository.findAll();
        BigDecimal jumlahNominalKegiatan = all.stream()
                .filter(kegiatan -> kegiatan.getBeban().getId().equals(id)).map(kegiatan -> kegiatan.getNominal())
                .reduce(BigDecimal.valueOf(0), (a, b) -> a.add(b));
        return jumlahNominalKegiatan;
    }
}
