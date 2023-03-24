package com.bni.report.service;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.repositories.BebanRepository;
import com.bni.report.repositories.KegiatanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class BebanService {
    @Autowired
    private BebanRepository bebanRepository;
    @Autowired
    private KegiatanRepository kegiatanRepository;
    @Autowired
    private KegiatanService kegiatanService;
    public List<Beban> getAll(){
        List<Beban> all = bebanRepository.findAll();
        all.stream().map(beban -> {
            Integer id = beban.getId();
            countSisa(id);
            return beban;
        }).collect(Collectors.toList());
        return all;
    }
    public Beban findById(Integer id){
        countSisa(id);
        return bebanRepository.findById(id).orElseThrow(RuntimeException::new);
    }
    public Beban create(Beban beban){
        return bebanRepository.save(beban);
    }
    public Beban edit(Beban beban){
        return bebanRepository.save(beban);
    }
    public void delete(Integer id){
        bebanRepository.deleteById(id);
    }
    public void countSisa(Integer id){
        Beban beban1 = bebanRepository.findById(id).orElse(new Beban());
        BigDecimal jumlahNominalKegiatan = kegiatanService.addNominalKegiatan(id);
        BigDecimal budget = beban1.getBudget();
        MathContext mc = new MathContext(10);
        beban1.setId(id);
        beban1.setSisa(budget.subtract(jumlahNominalKegiatan, mc));
        bebanRepository.save(beban1);
    }


}
