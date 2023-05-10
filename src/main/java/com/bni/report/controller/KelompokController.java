package com.bni.report.controller;

import com.bni.report.entities.Kelompok;
import com.bni.report.service.KelompokService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class KelompokController {

    @Autowired
    private KelompokService kelompokService;

    @GetMapping("/")
    public String getAll(Model model){
        List<Kelompok> kelompokList = kelompokService.getALl();
        model.addAttribute("kelompoks", kelompokList);
        return "index";
    }

    @PostMapping("/kelompok")
    public String add(Kelompok kelompok){
        kelompokService.save(kelompok);
        return "admin";
    }

}
