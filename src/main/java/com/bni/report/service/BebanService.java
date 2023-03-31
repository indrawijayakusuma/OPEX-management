package com.bni.report.service;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
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

    public Integer countBeban(){
        return Math.toIntExact(kegiatanRepository.count());
    }

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
    @PostConstruct
    public void addbeban() {
        List<Beban> bebanList = new ArrayList<>();
        bebanList.add(new Beban("beban penyuluhan", new BigDecimal(900000000), new Date()));
        bebanList.add(new Beban("beban sumbangan", new BigDecimal(800000000), new Date()));
        bebanList.add(new Beban("beban kantor", new BigDecimal(700000000), new Date()));
        bebanList.add(new Beban("beban perbaikan", new BigDecimal(300000000), new Date()));
        bebanList.add(new Beban("beban fasilitas", new BigDecimal(200000000), new Date()));
        bebanList.add(new Beban("beban ac", new BigDecimal(100000000), new Date()));
        bebanList.add(new Beban("beban peringatan", new BigDecimal(560000000), new Date()));
        bebanList.add(new Beban("beban event", new BigDecimal(870000000), new Date()));
        bebanList.add(new Beban("beban konsultasi", new BigDecimal(430000000), new Date()));
        bebanList.add(new Beban("beban konsumsi", new BigDecimal(6000000), new Date()));
        bebanList.add(new Beban("beban perbaikan", new BigDecimal(78000000), new Date()));
        bebanList.add(new Beban("beban surat", new BigDecimal(634000000), new Date()));
        bebanList.add(new Beban("beban operasional", new BigDecimal(654000000), new Date()));
        bebanList.add(new Beban("beban perioritas", new BigDecimal(900000000), new Date()));
        bebanList.add(new Beban("beban kesehatan", new BigDecimal(540000000), new Date()));
        bebanList.add(new Beban("beban peyuluhan", new BigDecimal(340000000), new Date()));
        bebanList.add(new Beban("beban promo", new BigDecimal(796000000), new Date()));

        bebanRepository.saveAll(bebanList);
    }

}
