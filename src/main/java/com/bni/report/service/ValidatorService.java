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

import java.util.List;

@Component
public class ValidatorService {
    @Autowired
    private ValidatorRepository validatorRepository;
    @Autowired
    private KegiatanRepository kegiatanRepository;

    public Page<Validator> paginateGetALl(int currPage, int pageSize, String sortDirection, String sortField){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage-1, pageSize, sort);
        return getAll(pageable);
    }

    public Page<Validator> getAll(Pageable pageable){
        return validatorRepository.findAll(pageable);
    }
    public Validator findById(Integer id){
        return validatorRepository.findById(id).orElseThrow(RuntimeException::new);
    }
    public Validator create(Validator validator){
        return validatorRepository.save(validator);
    }
    public Validator edit (Validator validator){
        Validator ObjectKegiatan = findById(validator.getId());
        return validatorRepository.save(validator);
    }
    public void delete(Integer id){
        validatorRepository.deleteById(id);
    }
    public void validate(Integer id){
        Kegiatan kegiatan = validatorRepository
                .findById(id)
                .map(Kegiatan::new).orElseThrow(() -> new RuntimeException());
        kegiatanRepository.save(kegiatan);
        validatorRepository.deleteById(id);
    }
}
