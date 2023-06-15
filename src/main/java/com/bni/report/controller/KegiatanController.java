package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.Program;
import com.bni.report.entities.Validator;
import com.bni.report.service.BebanService;
import com.bni.report.service.KegiatanService;
import com.bni.report.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class KegiatanController {
    @Autowired
    private KegiatanService kegiatanService;
    @Autowired
    private ProgramService programService;

    @GetMapping("/kegiatan/{id}")
    public String getAll(Model model, @PathVariable(value = "id") String id){
        return paginateGetAll(null, id,1,"budget", "asc",model);
    }
    @GetMapping("/kegiatan/page/{id}/{no}")
    public String paginateGetAll(
            @RequestParam(required = false) String keyword,
            @PathVariable(value = "id") String id,
            @PathVariable(value = "no") int currPage,
            @RequestParam(defaultValue = "budget") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model
    ){
        int pageSize = 15;
        Page<Kegiatan> kegiatanPage = null;
        if (keyword == null){
            kegiatanPage = kegiatanService.paginateGetALl(currPage, pageSize, sortDirection, sortField, id);
        }else{
            kegiatanPage = kegiatanService.paginateSearchingGetAll(currPage,pageSize,sortField,sortDirection, keyword, id);
        }

        List<Kegiatan> kegiatanList = new ArrayList<>();
        kegiatanList = kegiatanPage.getContent();

        Program program = programService.getById(id);
        Integer bebanId = program.getBeban().getId();

        model.addAttribute("keyword", keyword);
        model.addAttribute("sisa", 9000000);
        model.addAttribute("bebanId",bebanId);
        model.addAttribute("nameBeban", "name1");
        model.addAttribute("currentPage", currPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseDirection", sortDirection.equals("asc")?"desc":"asc");

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
        kegiatanService.create(kegiatan);
        return "redirect:/kegiatan/" + idProgram;
    }
    @PostMapping("/kegiatan")
    public String update(Kegiatan kegiatan){
        kegiatanService.create(kegiatan);
        kegiatanService.edit(kegiatan);
        return "redirect:/kegiatan/delete/" + kegiatan.getId();
    }
    @GetMapping("/kegiatan/delete/{id}")
    public String delete(@PathVariable Integer id){
//        Integer idBeban = kegiatanService.findById(id).getBeban().getId();
        kegiatanService.delete(id);
        return "redirect:/kegiatan/" + 1 ;
    }
}
