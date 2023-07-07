package com.bni.report.service;

import com.bni.report.entities.User;
import com.bni.report.entities.validators.ValidatorMataAnggaran;
import com.bni.report.repositories.ValidatorMataAnggaranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ValidatorMataAnggaranService {
    @Autowired
    private ValidatorMataAnggaranRepository mataAnggaranRepository;
    @Autowired
    private UserService userService;

    public Page<ValidatorMataAnggaran> paginateGetALl(int currPage, int pageSize, String sortDirection, String sortField, String user) {
        User userGet = userService.findByName(user).orElseThrow(() -> new RuntimeException("not found"));
        Integer id = userGet.getKelompok().getId();
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage - 1, pageSize, sort);
        return getAll(pageable, id);
    }

    public Page<ValidatorMataAnggaran> getAll(Pageable pageable, int kelompok) {
        List<ValidatorMataAnggaran> collect = mataAnggaranRepository.findAll().stream()
                .filter(MataAnggaran -> MataAnggaran.getKelompok().getId() == kelompok)
                .collect(Collectors.toList());
        return new PageImpl<>(collect);
    }

    public ValidatorMataAnggaran create(ValidatorMataAnggaran program) {
        return mataAnggaranRepository.save(program);
    }

    public Optional<ValidatorMataAnggaran> findByid(String id) {
        return mataAnggaranRepository.findById(id);
    }

    public void delete(String id) {
        mataAnggaranRepository.deleteById(id);
    }
}
