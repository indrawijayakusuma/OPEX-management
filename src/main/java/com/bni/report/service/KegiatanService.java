package com.bni.report.service;

import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.Validator;
import com.bni.report.repositories.KegiatanRepository;
import com.bni.report.repositories.ValidatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class KegiatanService {

    @Autowired
    private KegiatanRepository kegiatanRepository;

    @Autowired
    private ValidatorRepository validatorRepository;

    public List<Kegiatan> getsByBebanId(Integer id){
        return kegiatanRepository.findByBebanId(id);
    }
    public Page<Kegiatan> getAll(Pageable pageable,Integer id){
        return kegiatanRepository.findByBebanId(id,pageable);
    }

    public Page<Kegiatan> paginateGetALl(int currPage, int pageSize, String sortDirection, String sortField, Integer id){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage-1, pageSize,sort);
        return getAll(pageable, id);
    }

    public Page<Kegiatan> paginateSearchingGetAll(int currPage, int pageSize, String sortField, String sortDirection, String keyword, int id){
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

    public BigDecimal addNominalKegiatan(Integer id){
        List<Kegiatan> all = kegiatanRepository.findAll();
        return all.stream()
                .filter(kegiatan -> kegiatan.getBeban().getId().equals(id))
                .map(kegiatan -> kegiatan.getNominal())
                .reduce(BigDecimal.valueOf(0), (a, b) -> a.add(b));
    }

}
