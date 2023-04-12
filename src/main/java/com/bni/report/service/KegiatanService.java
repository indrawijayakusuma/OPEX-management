package com.bni.report.service;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.Validator;
import com.bni.report.repositories.KegiatanRepository;
import com.bni.report.repositories.ValidatorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component @Slf4j
public class KegiatanService {

    @Autowired
    private KegiatanRepository kegiatanRepository;

    @Autowired
    private ValidatorRepository validatorRepository;

    public Page<Kegiatan> getAll(Pageable pageable,Integer id){
        return kegiatanRepository.findByBebanId(id,pageable);
    }

    public Page<Kegiatan> paginateGetALl(int currPage, int pageSize, String sortDirection, String sortField, Integer id){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage-1, pageSize,sort);
        return getAll(pageable, id);
    }

    public Page<Kegiatan> paginateSearchingGetAll(int currPage, int pageSize, String sortField, String sortDirection, String keyword){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage-1, pageSize, sort);
        return kegiatanRepository.search(keyword,1,pageable);
    }

    public Kegiatan findById(Integer id){
        return kegiatanRepository.findById(id).orElseThrow(RuntimeException::new);
    }
    public Kegiatan create(Kegiatan kegiatan){
        return kegiatanRepository.save(kegiatan);
    }
    public void edit (Kegiatan kegiatan){
        Kegiatan objectKegiatan = findById(kegiatan.getId());
        Validator validator = Optional.of(objectKegiatan).map(Validator::new).get();
        validatorRepository.save(validator);
        kegiatanRepository.delete(objectKegiatan);
    }
    public void delete(Integer id){
        kegiatanRepository.deleteById(id);
    }

    public BigDecimal addNominalKegiatan(Integer id){
        List<Kegiatan> all = kegiatanRepository.findAll();
        BigDecimal jumlahNominalKegiatan = all.stream()
                .filter(kegiatan -> kegiatan.getBeban().getId().equals(id)).map(kegiatan -> kegiatan.getNominal())
                .reduce(BigDecimal.valueOf(0), (a, b) -> a.add(b));
        return jumlahNominalKegiatan;
    }


    public void addKegitan() {
        List<Kegiatan> kegitanList = new ArrayList<>();
        kegitanList.add(new Kegiatan("kegitan olahrage", new Beban(1),"cat1","suni", new BigDecimal(800000), new Date()));
        kegitanList.add(new Kegiatan("kegitan senam", new Beban(1),"cat1","julian", new BigDecimal(89000), new Date()));
        kegitanList.add(new Kegiatan("kegitan gym", new Beban(1),"cat1","brian", new BigDecimal(324000), new Date()));
        kegitanList.add(new Kegiatan("kegitan kebugaran", new Beban(1),"cat1","hasim", new BigDecimal(24235000), new Date()));

        kegitanList.add(new Kegiatan("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        kegitanList.add(new Kegiatan("kegitan senam", new Beban(2),"cat1","julian", new BigDecimal(89000), new Date()));
        kegitanList.add(new Kegiatan("kegitan gym", new Beban(2),"cat1","brian", new BigDecimal(324000), new Date()));
        kegitanList.add(new Kegiatan("kegitan kebugaran", new Beban(2),"cat1","hasim", new BigDecimal(24235000), new Date()));

        kegiatanRepository.saveAll(kegitanList);
    }
}
