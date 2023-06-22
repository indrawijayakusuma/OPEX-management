package com.bni.report.controller;

import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.Program;
import com.bni.report.entities.validators.Validator;
import com.bni.report.service.KegiatanService;
import com.bni.report.service.ProgramService;
import com.bni.report.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class KegiatanController {
    @Autowired
    private KegiatanService kegiatanService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private ValidatorService validatorService;

    @GetMapping("/kegiatan/{id}")
    public String getAll(Model model, @PathVariable(value = "id") String id ){
        return paginateGetAll(id,1,model);
    }
    @GetMapping("/kegiatan/page/{id}/{no}")
    public String paginateGetAll(
            @PathVariable(value = "id") String id,
            @PathVariable(value = "no") int currPage,
            Model model
    ){
        int pageSize = 15;
        Page<Kegiatan> kegiatanPage = null;
        kegiatanPage = kegiatanService.paginateGetALl(currPage, pageSize, id);

        List<Kegiatan> kegiatanList = new ArrayList<>();
        kegiatanList = kegiatanPage.getContent();

        Program program = programService.getById(id);
        Integer bebanId = program.getBeban().getId();
        BigDecimal sisaAkhir = kegiatanService.getSisa(id);

        model.addAttribute("sisa", sisaAkhir);
        model.addAttribute("bebanId",bebanId);
        model.addAttribute("nameBeban", "name1");
        model.addAttribute("currentPage", currPage);

        model.addAttribute("totalPages", kegiatanPage.getTotalPages());
        model.addAttribute("totalItems", kegiatanPage.getTotalElements());
        model.addAttribute("kegiatans", kegiatanList);

        model.addAttribute("kegiatan", new Kegiatan());

        return "listKegiatan";
    }
    @GetMapping("/kegiatan/update/{id}")
    public String formUpdateKegiatan(@PathVariable Integer id, Model model){
        Kegiatan byId = kegiatanService.findById(id);
//        Integer kelompokid = byId.getBeban().getKelompok().getId();
        model.addAttribute("kelompokId", 1);
        model.addAttribute("kegiatans", byId);
        return "formUpdateKegiatan";
    }
    @PostMapping("/kegiatan/{idProgram}")
    public String add(@PathVariable String idProgram, Kegiatan kegiatan){
        kegiatan.setProgram(new Program(idProgram));
        Validator validator = Optional.of(kegiatan).map(Validator::new).get();
        validatorService.create(validator);
        return "redirect:/kegiatan/" + idProgram;
    }
    @PostMapping("/kegiatan")
    public String update(Kegiatan kegiatan){
        Optional<Validator> validatorOpt = Optional.of(kegiatan).map(Validator::new);
        validatorOpt.ifPresent(value -> {
            Validator validator = value;
            validatorService.create(validator);
            kegiatanService.delete(kegiatan.getId());
        });
        return "redirect:/kegiatan/" + kegiatan.getProgram().getId();
    }
    @GetMapping("/kegiatan/delete/{id}")
    public String delete(@PathVariable Integer id){
        String idProgram = kegiatanService.findById(id).getProgram().getId();
        kegiatanService.delete(id);
        return "redirect:/kegiatan/" + idProgram ;
    }
}
