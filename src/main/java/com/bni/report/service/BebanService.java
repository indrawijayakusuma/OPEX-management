package com.bni.report.service;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.Validator;
import com.bni.report.repositories.BebanRepository;
import com.bni.report.repositories.KegiatanRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BebanService {
    @Autowired
    private BebanRepository bebanRepository;
    @Autowired
    private KegiatanRepository kegiatanRepository;
    @Autowired
    private KegiatanService kegiatanService;

    @Autowired
    private ValidatorService validatorService;

    public Page<Beban> getAll(Pageable pageable){
        Page<Beban> all = bebanRepository.findAll(pageable);
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
    public Page<Beban> paginateGetAll(int currPage, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage-1, pageSize, sort);
        return getAll(pageable);
    }

    public Page<Beban> paginateSearchingGetAll(int currPage, int pageSize, String sortField, String sortDirection, String keyword){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage-1, pageSize, sort);
        return bebanRepository.search(keyword, pageable);
    }


    @PostConstruct
    public void addbeban() {
        List<Beban> bebanList = new ArrayList<>();
        bebanList.add(new Beban("beban penyuluhan", new BigDecimal(900000000), new Date()));
        bebanList.add(new Beban("beban sumbangan", new BigDecimal(800000000), new Date()));


        List<Kegiatan> kegitanList = new ArrayList<>();
        kegitanList.add(new Kegiatan("kegitan olahrage", new Beban(1),"cat1","suni", new BigDecimal(800000), new Date()));
        kegitanList.add(new Kegiatan("kegitan senam", new Beban(1),"cat1","julian", new BigDecimal(89000), new Date()));
        kegitanList.add(new Kegiatan("kegitan gym", new Beban(1),"cat1","brian", new BigDecimal(324000), new Date()));
        kegitanList.add(new Kegiatan("kegitan kebugaran", new Beban(1),"cat1","hasim", new BigDecimal(24235000), new Date()));

        kegitanList.add(new Kegiatan("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        kegitanList.add(new Kegiatan("kegitan senam", new Beban(2),"cat1","julian", new BigDecimal(89000), new Date()));
        kegitanList.add(new Kegiatan("kegitan gym", new Beban(2),"cat1","brian", new BigDecimal(324000), new Date()));
        kegitanList.add(new Kegiatan("kegitan kebugaran", new Beban(2),"cat1","hasim", new BigDecimal(24235000), new Date()));

        bebanRepository.saveAll(bebanList);
        kegiatanRepository.saveAll(kegitanList);

//        validatorService.create(new Validator("test1", new Beban(1),"cat1","suni", new BigDecimal(800000), new Date()));
//        validatorService.create(new Validator("test2", new Beban(1),"cat1","suni", new BigDecimal(800000), new Date()));
    }
}
