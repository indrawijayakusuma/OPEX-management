package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.service.BebanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Controller
public class BebanController {
    @Autowired
    private BebanService bebanService;

    @GetMapping("/beban")
    public String getAll(Model model){

        return paginateGetAll(1,"name", "asc",model);
    }
    @GetMapping("/beban/page/{no}")
    public String paginateGetAll(
            @PathVariable(value = "no") int currPage,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model
    ){
        int pageSize = 9;
        Page<Beban> bebanPage = bebanService.paginateGetAll(currPage,pageSize,sortField,sortDirection);
        List<Beban> bebanList = new ArrayList<>();
        bebanList = bebanPage.getContent();

        model.addAttribute("currentPage", currPage);
        model.addAttribute("totalPages", bebanPage.getTotalPages());
        model.addAttribute("totalItems", bebanPage.getTotalElements());
        model.addAttribute("bebans", bebanList);

        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("sortField", sortField);
        model.addAttribute("reverseDirection", sortDirection.equals("asc")?"desc":"asc");

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
        return "redirect:/beban";
    }
    @PutMapping("/beban")
    public String formUpdateBeban1(Model model){
        return "redirect:/beban";
    }
    @GetMapping("/beban/update/{id}")
    public String formUpdateBeban(@PathVariable Integer id, Model model){
        Beban byId = bebanService.findById(id);
        model.addAttribute("bebans", byId);
        return "formUpdateBeban";
    }
    @GetMapping("/beban/delete/{id}")
    public String delete(@PathVariable Integer id){
        bebanService.delete(id);
        return "redirect:/beban";
    }
}
