package com.bni.report.controller;

import com.bni.report.entities.Kelompok;
import com.bni.report.entities.User;
import com.bni.report.repositories.UserRepository;
import com.bni.report.service.KelompokService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class KelompokController {

    @Autowired
    private KelompokService kelompokService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String getAll(Model model, Authentication authentication) {
        User byName = userRepository.findByName(authentication.getName()).orElseThrow(() -> new RuntimeException("user not found"));
        Integer id = byName.getKelompok().getId();
        List<Kelompok> kelompokList = kelompokService.getALl();
        model.addAttribute("kelompoks", kelompokList);
        return "index";
    }

    @PostMapping("/kelompok")
    public String add(Kelompok kelompok) {
        kelompokService.save(kelompok);
        return "admin";
    }

    @GetMapping("/kelompok/{id}")
    public String menu(@PathVariable Integer id, Model model) {
        model.addAttribute("idKelompok",id);
        return "menuMataAnggaran";
    }

}
