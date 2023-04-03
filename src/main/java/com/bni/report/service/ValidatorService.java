package com.bni.report.service;

import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.Validator;
import com.bni.report.repositories.KegiatanRepository;
import com.bni.report.repositories.ValidatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidatorService {
    @Autowired
    private ValidatorRepository validatorRepository;
    @Autowired
    private KegiatanRepository kegiatanRepository;

    public List<Validator> getAll(){
        return validatorRepository.findAll();
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
