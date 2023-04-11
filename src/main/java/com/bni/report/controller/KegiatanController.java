package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.Validator;
import com.bni.report.service.BebanService;
import com.bni.report.service.KegiatanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
    private BebanService bebanService;

    @GetMapping("/kegiatan/{id}")
    public String getAll(Model model, @PathVariable(value = "id") Integer id){
        return paginateGetAll(null,id,1,"name", "asc",model);
    }
    @GetMapping("/kegiatan/page/{name}/{no}")
    public String paginateGetAll(
            @RequestParam(required = false) String keyword,
            @PathVariable(value = "id") Integer id,
            @PathVariable(value = "no") int currPage,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model
    ){
        int pageSize = 9;
        Page<Kegiatan> kegiatanPage = null;
        if (keyword == null){
            kegiatanPage = kegiatanService.paginateGetALl(currPage, pageSize, sortDirection, sortField, id);
        }else{
            kegiatanPage = kegiatanService.paginateSearchingGetAll(currPage,pageSize,sortField,sortDirection, keyword);
        }

        List<Kegiatan> kegiatanList = new ArrayList<>();
        kegiatanList = kegiatanPage.getContent();

        Beban beban = bebanService.findById(id);
        BigDecimal sisa = beban.getSisa();
        String name1 = beban.getName();

        model.addAttribute("sisa", sisa);
        model.addAttribute("nameBeban", name1);
        model.addAttribute("currentPage", currPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseDirection", sortDirection.equals("asc")?"desc":"asc");

        model.addAttribute("totalPages", kegiatanPage.getTotalPages());
        model.addAttribute("totalItems", kegiatanPage.getTotalElements());
        model.addAttribute("kegiatans", kegiatanList);

        Validator validator = new Validator();
        validator.setBeban(new Beban(id));
        model.addAttribute("validatorsObject", validator);

        return "listKegiatan";
    }

    @PostMapping("/kegiatan")
    public String create(Kegiatan kegiatan){
        kegiatanService.create(kegiatan);
        return "redirect:/kegiatan";
    }

    @PutMapping("/kegiatan")
    public ResponseEntity<Kegiatan> edit(@RequestBody Kegiatan kegiatan){
        return ResponseEntity.ok().body(kegiatanService.edit(kegiatan));
    }
    @GetMapping("/kegiatan/delete/{id}")
    public String delete(@PathVariable Integer id){
        Integer idBeban = kegiatanService.findById(id).getBeban().getId();
        kegiatanService.delete(id);
        return "redirect:/kegiatan/" + idBeban ;
    }
}
