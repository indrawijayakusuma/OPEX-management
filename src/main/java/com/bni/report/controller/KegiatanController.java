package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
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
        return paginateGetAll(id,1,"name", "asc",model);
    }
    @GetMapping("/kegiatan/page/{id}/{no}")
    public String paginateGetAll(
            @PathVariable(value = "id") Integer id,
            @PathVariable(value = "no") int currPage,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model
    ){
        int pageSize = 9;
        Page<Kegiatan> kegiatanPage = kegiatanService.paginateGetALl(currPage, pageSize, sortDirection, sortField, id);
        List<Kegiatan> kegiatanList = new ArrayList<>();
        kegiatanList = kegiatanPage.getContent();

        Beban beban = bebanService.findById(id);
        BigDecimal sisa = beban.getSisa();
        String name = beban.getName();

        model.addAttribute("sisa", sisa);
        model.addAttribute("nameBeban", name);
        model.addAttribute("currentPage", currPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseDirection", sortDirection.equals("asc")?"desc":"asc");

        model.addAttribute("totalPages", kegiatanPage.getTotalPages());
        model.addAttribute("totalItems", kegiatanPage.getTotalElements());
        model.addAttribute("kegiatans", kegiatanList);

        return "listKegiatan";
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
