package com.bni.report.controller;

import com.bni.report.entities.Kelompok;
import com.bni.report.entities.MataAnggaran;
import com.bni.report.entities.validators.ValidatorMataAnggaran;
import com.bni.report.service.MataAnggaranService;
import com.bni.report.service.ValidatorMataAnggaranService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller @Slf4j
public class MataAnggaranController {
    @Autowired
    private MataAnggaranService mataAnggaranService;
    @Autowired
    private ValidatorMataAnggaranService validatorMataAnggaranService;

    @GetMapping("/mataanggaran/form/{kelompokId}")
    public String addForm(Model model, @PathVariable int kelompokId){
        model.addAttribute("mataAnggaran", new MataAnggaran());
        return "AddMataAnggaran";
    }
    @PostMapping("/mataanggaran/{kelompokId}")
    public String add(@Valid @ModelAttribute(value="mataAnggaran") MataAnggaran mataAnggaran, BindingResult result, @PathVariable Integer kelompokId){
        if(result.hasErrors()){
            return "addMataAnggaran";
        }
        log.info(String.valueOf(mataAnggaran));
        mataAnggaran.setKelompok(new Kelompok(kelompokId));
        Optional.of(mataAnggaran).map(ValidatorMataAnggaran::new).ifPresent(validatorMataAnggaran -> {
            validatorMataAnggaranService.create(validatorMataAnggaran);
        });
        return "redirect:/beban/" + kelompokId;
    }
}
