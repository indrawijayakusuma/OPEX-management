package com.bni.report.service;

import com.bni.report.entities.User;
import com.bni.report.entities.validators.ValidatorBeban;
import com.bni.report.entities.validators.ValidatorProgram;
import com.bni.report.repositories.ValidatorBebanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ValidatorBebanService {
    @Autowired
    ValidatorBebanRepository validatorBebanRepository;
    @Autowired
    private UserService userService;

    public Page<ValidatorBeban> paginateGetALl(int currPage, int pageSize, String sortDirection, String sortField, String user){
        User userGet = userService.findByName(user).orElseThrow(() -> new RuntimeException("not found"));
        Integer id = userGet.getKelompok().getId();
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage-1, pageSize, sort);
        return getAll(pageable,id);
    }
    public Page<ValidatorBeban> getAll(Pageable pageable, int kelompok){
        List<ValidatorBeban> collect = validatorBebanRepository.findAll().stream()
                .filter(beban -> beban.getKelompok().getId() == kelompok)
                .collect(Collectors.toList());
        return new PageImpl<>(collect);
    }
    public ValidatorBeban create(ValidatorBeban program){
        return validatorBebanRepository.save(program);
    }
    public Optional<ValidatorBeban> findByid(Integer id){
        return validatorBebanRepository.findById(id);
    }
    public void delete(Integer id){
        validatorBebanRepository.deleteById(id);
    }
}
