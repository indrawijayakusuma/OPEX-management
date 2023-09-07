package com.bni.report.service;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Program;
import com.bni.report.repositories.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ProgramService {
    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private KegiatanService kegiatanService;

    public Page<Program> getAll(Pageable pageable, Integer id) {
        Page<Program> byBebanId = programRepository.getAllByBebanId(id, pageable);
        List<Program> programs = byBebanId.stream().peek(program -> {
            BigDecimal sisa = kegiatanService.getSisa(program.getId());
            BigDecimal sumRealisasi = kegiatanService.sumRealisasi(program.getId());
            program.setSisa(sisa);
            program.setRealisasi(sumRealisasi);
            programRepository.save(program);
        }).toList();
        return new PageImpl<>(programs);
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

    public Page<Program> getAllUnvalidate(Integer idKelompok, Pageable pageable){
        return programRepository.getUnValidateBeban(idKelompok, pageable);
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
    public Optional<Program> getByIdOpt(String id) {
        return programRepository.findById(id);
    }

    public BigDecimal addNominalProgram(Integer id) {
        return programRepository.findAll().stream()
                .filter(program -> Objects.equals(program.getBeban().getId(), id))
                .map(Program::getBudget)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }
}