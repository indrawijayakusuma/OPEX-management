package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.service.BebanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class BebanController {
    @Autowired
    private BebanService bebanService;

    @GetMapping("/beban")
    public String getAll(Model model){
        List<Beban> all = bebanService.getAll();
        model.addAttribute("bebans", all);
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
    @GetMapping("/beban/{id}")
    public ResponseEntity<Beban> getById(@PathVariable Integer id){
        return null;
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
