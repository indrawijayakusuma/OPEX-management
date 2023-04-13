package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Validator;
import com.bni.report.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
public class ValidatorController {

    @Autowired
    private ValidatorService validatorService;

    @GetMapping("/validator")
    public String getAll(Model model){
        final List<Validator> all = validatorService.getAll();
        model.addAttribute("valids", all);
        return "validasi1";
    }

    @GetMapping("/validator/addform/{id}")
    public String addForm(@PathVariable Integer id, Model model){
        Validator validator = new Validator();
        validator.setBeban(new Beban(id));
        model.addAttribute("validators", validator);
        return "formAddKegiatan";
    }
    @PostMapping("/validator")
    public String add(Validator validator){
        validatorService.create(validator);
        return "redirect:/beban";
    }

    @GetMapping("/validator/validate/{id}")
    public String validate(@PathVariable Integer id){
        validatorService.validate(id);
        return "redirect:/validator";
    }

//    @GetMapping("/validator/{id}")
//    public ResponseEntity<Validator> getById(@PathVariable Integer id){
//        return ResponseEntity.ok().body(validatorService.findById(id));
//    }
//    @DeleteMapping("/validator/{id}")
//    public ResponseEntity<?> delete(@PathVariable Integer id){
//        validatorService.delete(id);
//        return ResponseEntity.ok().body("deleted");
//    }
    @PutMapping("/validator")
    public ResponseEntity<Validator> edit(@RequestBody Validator validator){
        return ResponseEntity.ok().body(validatorService.edit(validator));
    }

}
