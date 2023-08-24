package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kelompok;
import com.bni.report.entities.MataAnggaran;
import com.bni.report.entities.dto.MataanggaranInputDTO;
import com.bni.report.entities.validators.ValidatorMataAnggaran;
import com.bni.report.service.BebanService;
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

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class MataAnggaranController {
    @Autowired
    private BebanService bebanService;
    @Autowired
    private MataAnggaranService mataAnggaranService;
    @Autowired
    private ValidatorMataAnggaranService validatorMataAnggaranService;

    @GetMapping("/mataanggaran/form/{kelompokId}")
    public String addForm(Model model, @PathVariable int kelompokId) {
        model.addAttribute("mataAnggaran", new MataAnggaran());
        return "AddMataAnggaran";
    }

    @GetMapping("/mataanggaran/form/edit/{kelompokId}")
    public String editForm(Model model, @PathVariable int kelompokId){
        List<String> mataAnggaranList = mataAnggaranService.getAll(kelompokId)
                .stream()
                .map(MataAnggaran::getMataAnggaran)
                .toList();
        model.addAttribute("listAnggaran", mataAnggaranList);
        model.addAttribute("editMataAnggaran", new MataanggaranInputDTO());

        return "editMataAnggaran";
    }

    @PostMapping("/mataanggaran/edit")
    public String edit(@Valid @ModelAttribute(value = "mataAnggaran") MataanggaranInputDTO mataanggaranInputDTO, BindingResult result){
        if (result.hasErrors()) {
            return "editMataAnggaran";
        }
        String mataAnggaranEdit = mataanggaranInputDTO.getMataAnggaranEdit();

        Beban bebanBudgetMataanggaran = bebanService.getByNamaMataanggaran(mataAnggaranEdit);
        if (bebanBudgetMataanggaran != null){
            bebanBudgetMataanggaran.setNomerRekening(mataanggaranInputDTO.getNomerRekening());
            bebanBudgetMataanggaran.setName(mataanggaranInputDTO.getMataAnggaran());
            bebanService.create(bebanBudgetMataanggaran);
        }

        MataAnggaran byMataAnggaran = mataAnggaranService.getByMataAnggaran(mataAnggaranEdit);
        byMataAnggaran.setNomerRekening(mataanggaranInputDTO.getNomerRekening());
        byMataAnggaran.setMataAnggaran(mataanggaranInputDTO.getMataAnggaran());

        mataAnggaranService.create(byMataAnggaran);

        return "redirect:/beban/" + byMataAnggaran.getKelompok().getId();
    }

    @PostMapping("/mataanggaran/{kelompokId}")
    public String add(@Valid @ModelAttribute(value = "mataAnggaran") MataAnggaran mataAnggaran, BindingResult result, @PathVariable Integer kelompokId) {
        if (result.hasErrors()) {
            return "addMataAnggaran";
        }
        mataAnggaran.setKelompok(new Kelompok(kelompokId));
        mataAnggaranService.create(mataAnggaran);
//        Optional.of(mataAnggaran).map(ValidatorMataAnggaran::new).ifPresent(validatorMataAnggaran -> {
//            validatorMataAnggaranService.create(validatorMataAnggaran);
//        });
        return "redirect:/beban/" + kelompokId;
    }
}
