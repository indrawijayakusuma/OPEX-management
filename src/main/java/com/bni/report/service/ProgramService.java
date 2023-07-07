package com.bni.report.service;

import com.bni.report.entities.Program;
import com.bni.report.repositories.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class ProgramService {
    @Autowired
    private ProgramRepository programRepository;

    public Page<Program> getAll(Pageable pageable, Integer id) {
        return programRepository.findByBebanId(id, pageable);
    }

    public Page<Program> paginateGetAll(int currPage, int pageSize, String sortField, String sortDirection, Integer id) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage - 1, pageSize, sort);
        return getAll(pageable, id);
    }

    public Page<Program> paginateSearchingGetAll(int currPage, int pageSize, String sortField, String sortDirection, String keyword, int id) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currPage - 1, pageSize, sort);
        return programRepository.search(keyword, id, pageable);
    }

    public Program create(Program program) {
        return programRepository.save(program);
    }

    public void delete(String id) {
        programRepository.deleteById(id);
    }

    public Program getById(String id) {
        return programRepository.findById(id).get();
    }

    public BigDecimal addNominalProgram(Integer id) {
        return programRepository.findAll().stream()
                .filter(program -> Objects.equals(program.getBeban().getId(), id))
                .map(Program::getBudget)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }
}