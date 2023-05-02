package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.service.BebanService;
import com.bni.report.service.KelompokService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BebanController {
    @Autowired
    private BebanService bebanService;
    @Autowired
    private KelompokService kelompokService;

    @GetMapping("/beban/{id}")
    public String getAll(@PathVariable Integer id, Model model){
        return paginateGetAll(null, id,1,"name", "asc", model);
    }
    @GetMapping("/beban/page/{id}/{no}")
    public String paginateGetAll(
            @RequestParam(required = false) String keyword,
            @PathVariable(value = "id") Integer idKelompok,
            @PathVariable(value = "no") int currPage,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model
    ){
        int pageSize = 15;
        Page<Beban> bebanPage = null;
        if (keyword == null){
            bebanPage = bebanService.paginateGetAll(currPage,pageSize,sortField,sortDirection,idKelompok);
        }else{
            bebanPage = bebanService.paginateSearchingGetAll(currPage,pageSize,sortField,sortDirection,keyword,idKelompok);
        }
        List<Beban> bebanList = new ArrayList<>();
        bebanList = bebanPage.getContent();

        String nameKelompok = kelompokService.findById(idKelompok).getName();

        model.addAttribute("currentPage", currPage);
        model.addAttribute("totalPages", bebanPage.getTotalPages());
        model.addAttribute("totalItems", bebanPage.getTotalElements());
        model.addAttribute("bebans", bebanList);

        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("sortField", sortField);
        model.addAttribute("reverseDirection", sortDirection.equals("asc")?"desc":"asc");
        model.addAttribute("nameKelompok", nameKelompok);
        model.addAttribute("keyword", keyword);

        // add form
        Beban beban = new Beban();
        model.addAttribute("bebansAdd", beban);

        return "index";
    }

    @GetMapping("/beban/addform")
    public String addForm(Model model){
        Beban beban = new Beban();
        model.addAttribute("bebans", beban);
        return "formAddBeban";
    }

    @PostMapping("/beban")
    public String add(Beban beban){
        bebanService.create(beban);
        return "redirect:/beban" + beban.getId();
    }

    @GetMapping("/beban/update/{id}")
    public String formUpdateBeban(@PathVariable Integer id, Model model){
        Beban byId = bebanService.findById(id);
        Integer kelompokId = byId.getKelompok().getId();
        model.addAttribute("bebans", byId);
        model.addAttribute("kelompokId", kelompokId);
        return "formUpdateBeban";
    }
    @GetMapping("/beban/delete/{id}")
    public String delete(@PathVariable Integer id){
        Beban beban = bebanService.findById(id);
        bebanService.delete(id);
        return "redirect:/beban/" + beban.getKelompok().getId();
    }

}
