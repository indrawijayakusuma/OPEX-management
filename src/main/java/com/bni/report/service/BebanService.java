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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @Autowired
    private ProgramService programService;
    @Autowired
    private MataAnggaranRepository mataAnggaranRepository;
    @Autowired
    private ProgramRepository programRepository;

    public List<Beban> getByKelompokId(Integer id) {
        return bebanRepository.findByKelompokId(id);
    }

    public Page<Beban> getAll(Pageable pageable, Integer id) {
        Page<Beban> all = bebanRepository.findByKelompokId(id, pageable);
        all.stream().map(beban -> {
            Integer idBeban = beban.getId();
            countSisa(idBeban);
            return beban;
        }).collect(Collectors.toList());
        return all;
    }

    public Page<Beban> paginateGetAll(int currPage, int pageSize, String sortField, String sortDirection, Integer id) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage - 1, pageSize, sort);
        return getAll(pageable, id);
    }

    public Page<Beban> paginateSearchingGetAll(int currPage, int pageSize, String sortField, String sortDirection, String keyword, Integer id) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage - 1, pageSize, sort);
        return bebanRepository.search(keyword, pageable, id);
    }

    public Beban findById(Integer id) {
        countSisa(id);
        return bebanRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Beban create(Beban beban) {
        return bebanRepository.save(beban);
    }

    public Beban getByNamaMataanggaran(String mataAnggaran) {
        return bebanRepository.findByName(mataAnggaran);
    }

    public Beban edit(Beban beban) {
        return bebanRepository.save(beban);
    }

    public void delete(Integer id) {
        bebanRepository.deleteById(id);
    }

    public void countSisa(Integer id) {
        Beban beban1 = bebanRepository.findById(id).orElse(new Beban());
        BigDecimal jumlahNominalKegiatan = programService.addNominalProgram(id);
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
        users.add(new User(7, "validator", "$2a$12$SfBMDogva22862CCfL0E9Oi3AUftOXbAfHcNs6UCDGQpq25P3GQMi", "ADMIN", new Kelompok(1)));
        users.add(new User(5, "inputer", "$2a$12$SfBMDogva22862CCfL0E9Oi3AUftOXbAfHcNs6UCDGQpq25P3GQMi", "INPUTER", new Kelompok(1)));
        users.add(new User(7, "user", "$2a$12$SfBMDogva22862CCfL0E9Oi3AUftOXbAfHcNs6UCDGQpq25P3GQMi", "USER", new Kelompok(1)));
        users.add(new User(7, "super admin", "$2a$12$SfBMDogva22862CCfL0E9Oi3AUftOXbAfHcNs6UCDGQpq25P3GQMi", "SUPER_ADMIN", new Kelompok(1)));

        List<Kelompok> kelompokList = new ArrayList<>();
        kelompokList.add(new Kelompok("Mass & Payroll"));
        kelompokList.add(new Kelompok("Affluent & Uppermass"));
        kelompokList.add(new Kelompok("Business Owner"));

        List<MataAnggaran> mataAnggarans = new ArrayList<>();
        mataAnggarans.add(new MataAnggaran("743360550009001", "Beban Keperluan Kantor", new Kelompok(1)));

        List<Beban> bebanList = new ArrayList<>();
        bebanList.add(new Beban(8, "743360550009001", "Beban Keperluan Kantor", new Kelompok(1), new BigDecimal(90000000), new Date()));

        List<Program> programs = new ArrayList<>();
        programs.add(new Program("programs", "Beban Rapat Kerja", new BigDecimal(6390080), "dedi", "123234", new Beban(1)));

        List<Kegiatan> kegitanList = new ArrayList<>();
//        kegitanList.add(new Kegiatan(34, new Program("6a38710b-56f0-45ac-9c4c-aa0967e5f257"), new BigDecimal(336700), new Date()));
//        kegitanList.add(new Kegiatan(32, new Program("6a38710b-56f0-45ac-9c4c-aa0967e5f257"), new BigDecimal(597000), new Date()));
//        kegitanList.add(new Kegiatan(45, new Program("6a38710b-56f0-45ac-9c4c-aa0967e5f257"), new BigDecimal(426000), new Date()));
//        kegitanList.add(new Kegiatan(643, new Program("6a38710b-56f0-45ac-9c4c-aa0967e5f257"), new BigDecimal(315750), new Date()));
//        kegitanList.add(new Kegiatan(546, new Program("6a38710b-56f0-45ac-9c4c-aa0967e5f257"), new BigDecimal(425998), new Date()));
//        kegitanList.add(new Kegiatan(425, new Program("6a38710b-56f0-45ac-9c4c-aa0967e5f257"), new BigDecimal(602000), new Date()));
//        kegitanList.add(new Kegiatan(6463, new Program("6a38710b-56f0-45ac-9c4c-aa0967e5f257"), new BigDecimal(185000), new Date()));
//        kegitanList.add(new Kegiatan(86, new Program("6a38710b-56f0-45ac-9c4c-aa0967e5f257"), new BigDecimal(433276), new Date()));
//        kegitanList.add(new Kegiatan(97, new Program("6a38710b-56f0-45ac-9c4c-aa0967e5f257"), new BigDecimal(395000), new Date()));
//        kegitanList.add(new Kegiatan(72, new Program("6a38710b-56f0-45ac-9c4c-aa0967e5f257"), new BigDecimal(534000), new Date()));
//        kegitanList.add(new Kegiatan(17, new Program("6a38710b-56f0-45ac-9c4c-aa0967e5f257"), new BigDecimal(400000), new Date()));
//        kegitanList.add(new Kegiatan(74, new Program("6a38710b-56f0-45ac-9c4c-aa0967e5f257"), new BigDecimal(168000), new Date()));
//        kegitanList.add(new Kegiatan(35, new Program("6a38710b-56f0-45ac-9c4c-aa0967e5f257"), new BigDecimal(229600), new Date()));

//        kelompokRepository.saveAll(kelompokList);
//        mataAnggaranRepository.saveAll(mataAnggarans);
//        bebanRepository.saveAll(bebanList);
//        programRepository.saveAll(programs);
//        userRepository.saveAll(users);
//        kegiatanRepository.saveAll(kegitanList);
    }
}
