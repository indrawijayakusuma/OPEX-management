package com.bni.report.service;

import com.bni.report.entities.*;
import com.bni.report.repositories.*;
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
    private KelompokRepository kelompokRepository;
    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private UserService userRepository;
    @Autowired
    private KegiatanService kegiatanService;

    @Autowired
    private ValidatorService validatorService;

    public Page<Beban> getAll(Pageable pageable,Integer id){
        Page<Beban> all = bebanRepository.findByKelompokId(id, pageable);
        all.stream().map(beban -> {
            Integer idBeban = beban.getId();
            countSisa(idBeban);
            return beban;
        }).collect(Collectors.toList());
        return all;
    }

    public Page<Beban> paginateGetAll(int currPage, int pageSize, String sortField, String sortDirection, Integer id){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage-1, pageSize, sort);
        return getAll(pageable, id);
    }

    public Page<Beban> paginateSearchingGetAll(int currPage, int pageSize, String sortField, String sortDirection, String keyword, Integer id){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage-1, pageSize, sort);
        return bebanRepository.search(keyword, pageable, id);
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
        beban1.setRealisasi(jumlahNominalKegiatan);
        beban1.setSisa(budget.subtract(jumlahNominalKegiatan, mc));
        bebanRepository.save(beban1);
    }


    @PostConstruct
    public void addbeban() {

        List<User> users = new ArrayList<>();
        users.add(new User(7,"validator","$2a$12$SfBMDogva22862CCfL0E9Oi3AUftOXbAfHcNs6UCDGQpq25P3GQMi","ADMIN"));
        users.add(new User(5,"inputer","$2a$12$SfBMDogva22862CCfL0E9Oi3AUftOXbAfHcNs6UCDGQpq25P3GQMi","INPUTER"));
        users.add(new User(7,"user","$2a$12$SfBMDogva22862CCfL0E9Oi3AUftOXbAfHcNs6UCDGQpq25P3GQMi","USER"));
        users.add(new User(7,"super admin","$2a$12$SfBMDogva22862CCfL0E9Oi3AUftOXbAfHcNs6UCDGQpq25P3GQMi","SUPER_ADMIN"));
        userRepository.save(new User(8,"user1","test","USER"));

        List<Kelompok> kelompokList = new ArrayList<>();
        kelompokList.add(new Kelompok("DCU"));
        kelompokList.add(new Kelompok("DEP"));
        kelompokList.add(new Kelompok("FPM"));
        kelompokList.add(new Kelompok("FPD 1"));
        kelompokList.add(new Kelompok("FPD 2"));
        kelompokList.add(new Kelompok("FPD 3"));


        List<Beban> bebanList = new ArrayList<>();
        bebanList.add(new Beban("23424242424","beban DCU 1", new BigDecimal(900000000), new Date(), new Kelompok(1)));
        bebanList.add(new Beban("23424242424","test1", new BigDecimal(900000000), new Date(), new Kelompok(1)));
        bebanList.add(new Beban("23424242424","test2", new BigDecimal(800000000), new Date(), new Kelompok(1)));
        bebanList.add(new Beban("23424242424","test3", new BigDecimal(900000000), new Date(), new Kelompok(2)));
        bebanList.add(new Beban("23424242424","test4", new BigDecimal(800000000), new Date(), new Kelompok(2)));
        bebanList.add(new Beban("23424242424","test5", new BigDecimal(900000000), new Date(), new Kelompok(2)));
        bebanList.add(new Beban("23424242424","test6", new BigDecimal(800000000), new Date(), new Kelompok(1)));
        bebanList.add(new Beban("23424242424","test7", new BigDecimal(900000000), new Date(), new Kelompok(2)));


        List<Kegiatan> kegitanList = new ArrayList<>();
        kegitanList.add(new Kegiatan("kegitan dcu olahrage", new Beban(1),"cat1","suni", new BigDecimal(800000), new Date()));
        kegitanList.add(new Kegiatan("kegitan dcu senam", new Beban(1),"cat1","julian", new BigDecimal(89000), new Date()));
        kegitanList.add(new Kegiatan("kegitan dcu gym", new Beban(1),"cat1","brian", new BigDecimal(324000), new Date()));
        kegitanList.add(new Kegiatan("kegitan dcu kebugaran", new Beban(1),"cat1","hasim", new BigDecimal(24235000), new Date()));
        kegitanList.add(new Kegiatan("kegitan dcu olahrage", new Beban(1),"cat1","suni", new BigDecimal(800000), new Date()));
        kegitanList.add(new Kegiatan("kegitan dcu senam", new Beban(1),"cat1","julian", new BigDecimal(89000), new Date()));
        kegitanList.add(new Kegiatan("kegitan dcu gym", new Beban(1),"cat1","brian", new BigDecimal(324000), new Date()));
        kegitanList.add(new Kegiatan("kegitan dcu kebugaran", new Beban(1),"cat1","hasim", new BigDecimal(24235000), new Date()));
        kegitanList.add(new Kegiatan("kegitan dcu olahrage", new Beban(1),"cat1","suni", new BigDecimal(800000), new Date()));
        kegitanList.add(new Kegiatan("kegitan dcu senam", new Beban(1),"cat1","julian", new BigDecimal(89000), new Date()));
        kegitanList.add(new Kegiatan("kegitan dcu gym", new Beban(1),"cat1","brian", new BigDecimal(324000), new Date()));


        kegitanList.add(new Kegiatan("kegitan test1 olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        kegitanList.add(new Kegiatan("kegitan test1 senam", new Beban(2),"cat1","julian", new BigDecimal(89000), new Date()));
        kegitanList.add(new Kegiatan("kegitan test1 gym", new Beban(2),"cat1","brian", new BigDecimal(324000), new Date()));
        kegitanList.add(new Kegiatan("kegitan test1 olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        kegitanList.add(new Kegiatan("kegitan test1 senam", new Beban(2),"cat1","julian", new BigDecimal(89000), new Date()));
        kegitanList.add(new Kegiatan("kegitan test1 kebugaran1", new Beban(2),"cat1","hasim", new BigDecimal(24235000), new Date()));
        kegitanList.add(new Kegiatan("kegitan test1 kebugaran2", new Beban(2),"cat1","hasim", new BigDecimal(24235000), new Date()));
        kegitanList.add(new Kegiatan("kegitan test1 kebugaran3", new Beban(2),"cat1","hasim", new BigDecimal(24235000), new Date()));



        List<Validator> validatorList = new ArrayList<>();
        validatorList.add(new Validator("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        validatorList.add(new Validator("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        validatorList.add(new Validator("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        validatorList.add(new Validator("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        validatorList.add(new Validator("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        validatorList.add(new Validator("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        validatorList.add(new Validator("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        validatorList.add(new Validator("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        validatorList.add(new Validator("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        validatorList.add(new Validator("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        validatorList.add(new Validator("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        validatorList.add(new Validator("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));
        validatorList.add(new Validator("kegitan olahrage", new Beban(2),"cat1","suni", new BigDecimal(800000), new Date()));

        kelompokRepository.saveAll(kelompokList);
        bebanRepository.saveAll(bebanList);
        kegiatanRepository.saveAll(kegitanList);
        userRepository.saveAll(users);
//        validatorRepository.saveAll(validatorList);
    }
}
