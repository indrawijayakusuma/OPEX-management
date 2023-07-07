package com.bni.report.service;

import com.bni.report.entities.User;
import com.bni.report.entities.validators.ValidatorProgram;
import com.bni.report.repositories.ValidatorProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ValidatorProgramService {
    @Autowired
    private ValidatorProgramRepository validatorProgramRepository;
    @Autowired
    private UserService userService;

    public Page<ValidatorProgram> paginateGetALl(int currPage, int pageSize, String sortDirection, String sortField, String user) {
        User userGet = userService.findByName(user).orElseThrow(() -> new RuntimeException("not found"));
        Integer id = userGet.getKelompok().getId();
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage - 1, pageSize, sort);
        return getAll(pageable, id);
    }

    public Page<ValidatorProgram> getAll(Pageable pageable, int kelompok) {
        List<ValidatorProgram> collect = validatorProgramRepository.findAll().stream()
                .filter(validator -> validator.getBeban().getKelompok().getId() == kelompok)
                .collect(Collectors.toList());
        return new PageImpl<>(collect);
    }

    public ValidatorProgram create(ValidatorProgram program) {
        return validatorProgramRepository.save(program);
    }

    public Optional<ValidatorProgram> findByid(String id) {
        return validatorProgramRepository.findById(id);
    }

    public void delete(String id) {
        validatorProgramRepository.deleteById(id);
    }
}
