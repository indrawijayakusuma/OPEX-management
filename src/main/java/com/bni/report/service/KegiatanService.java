package com.bni.report.service;

import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.Program;
import com.bni.report.entities.Validator;
import com.bni.report.repositories.KegiatanRepository;
import com.bni.report.repositories.ValidatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class KegiatanService {

    @Autowired
    private KegiatanRepository kegiatanRepository;

    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private ProgramService programService;


    public List<Kegiatan> getByProgramId(String id){
        return kegiatanRepository.findByProgramId(id);
    }
    public Page<Kegiatan> getAll(Pageable pageable,String id){
        Page<Kegiatan> program = kegiatanRepository.findByProgramId(id, pageable);
//        program.stream().min(Comparator.comparing(Kegiatan::getDate))
//                .map(kegiatan -> {
//                    kegiatan.setBudget(kegiatan.getProgram().getBudget());
//                    BigDecimal sisaFirstKegiatan = kegiatan.getBudget().subtract(kegiatan.getRealisasi());
//                    kegiatan.setSisa(sisaFirstKegiatan);
//                    kegiatan.getBudget();
//                }).get();
        Program programId = programService.getById(id);
        final BigDecimal[] temp = {programId.getBudget()};
        List<Kegiatan> collect = program.stream()
                .sorted(Comparator.comparing(Kegiatan::getDate))
                .map(kegiatan -> {
                    kegiatan.setBudget(temp[0]);
                    BigDecimal sisaFirstKegiatan = kegiatan.getBudget().subtract(kegiatan.getRealisasi());
                    kegiatan.setSisa(sisaFirstKegiatan);
                    temp[0] = kegiatan.getSisa();
                    return kegiatan;
                }).collect(Collectors.toList());
        return new PageImpl<>(collect);
    }
    public Page<Kegiatan> paginateGetALl(int currPage, int pageSize, String sortDirection, String sortField, String id){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage-1, pageSize,sort);
        return getAll(pageable, id);
    }
    public Page<Kegiatan> paginateSearchingGetAll(int currPage, int pageSize, String sortField, String sortDirection, String keyword, String id){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage-1, pageSize, sort);
        return kegiatanRepository.search(keyword,id,pageable);
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
    }
    public void delete(Integer id){
        kegiatanRepository.deleteById(id);
    }

    public void setbudgetSisaProgram(Integer id){

    }

//    public BigDecimal addNominalKegiatan(Integer id){
//        List<Kegiatan> a  ll = kegiatanRepository.findAll();
//        return all.stream()
//                .filter(kegiatan -> kegiatan.getBeban().getId().equals(id))
//                .map(kegiatan -> kegiatan.getNominal())
//                .reduce(BigDecimal.valueOf(0), (a, b) -> a.add(b));
//    }
}
