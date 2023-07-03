package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kelompok;
import com.bni.report.entities.MataAnggaran;
import com.bni.report.entities.validators.ValidatorBeban;
import com.bni.report.service.BebanService;
import com.bni.report.service.KelompokService;
import com.bni.report.service.MataAnggaranService;
import com.bni.report.service.ValidatorBebanService;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller @Slf4j
public class BebanController {
    @Autowired
    private BebanService bebanService;
    @Autowired
    private KelompokService kelompokService;
    @Autowired
    private MataAnggaranService mataAnggaranService;
    @Autowired
    private ValidatorBebanService validatorBebanService;

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

        List<String> all = mataAnggaranService.getAll(idKelompok).stream()
                .map(MataAnggaran::getMataAnggaran)
                .collect(Collectors.toList());

        model.addAttribute("currentPage", currPage);
        model.addAttribute("totalPages", bebanPage.getTotalPages());
        model.addAttribute("totalItems", bebanPage.getTotalElements());
        model.addAttribute("bebans", bebanList);

        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("sortField", sortField);
        model.addAttribute("reverseDirection", sortDirection.equals("asc")?"desc":"asc");
        model.addAttribute("nameKelompok", nameKelompok);
        model.addAttribute("keyword", keyword);
        model.addAttribute("bebansAdd", new Beban());
        model.addAttribute("mataAnggaranAdd",new MataAnggaran());
        model.addAttribute("mataAnggarans", all);

        return "ListBeban";
    }

    @GetMapping("/beban/addform")
    public String addForm(Model model){
        Beban beban = new Beban();
        model.addAttribute("bebans", beban);
        return "formAddBeban";
    }
    @PostMapping("/beban/{kelompokId}")
    public String add(Beban beban, @PathVariable Integer kelompokId){
        String nomerRekening = mataAnggaranService.getNomerRekening(beban.getName());
        beban.setKelompok(new Kelompok(kelompokId));
        beban.setNomerRekening(nomerRekening);
        Optional.of(beban).map(ValidatorBeban::new).ifPresent(beban1 -> {
            validatorBebanService.create(beban1);
            bebanService.delete(beban1.getId());
        });
        return "redirect:/beban/" + kelompokId;
    }
    @GetMapping("/beban/update/{id}")
    public String formUpdateBeban(@PathVariable Integer id, Model model){
        Beban byId = bebanService.findById(id);
        Integer kelompokId = byId.getKelompok().getId();
        List<String> all = mataAnggaranService.getAll(kelompokId)
                .stream()
                .map(MataAnggaran::getMataAnggaran)
                .toList();
        model.addAttribute("name", byId.getName());
        model.addAttribute("bebans", byId);
        model.addAttribute("kelompokId", kelompokId);
        model.addAttribute("mataAnggarans", all);
        return "formUpdateBeban";
    }
    @PostMapping("/beban/update")
    public String update(Beban beban){
        String nomerRekening = mataAnggaranService.getNomerRekening(beban.getName());
        beban.setNomerRekening(nomerRekening);
        ValidatorBeban validatorBeban = Optional.of(beban).map(ValidatorBeban::new).get();
        validatorBebanService.create(validatorBeban);
        bebanService.delete(beban.getId());
        return "redirect:/beban/" + beban.getKelompok().getId();
    }
    @GetMapping("/beban/delete/{id}")
    public String delete(@PathVariable Integer id){
        Beban beban = bebanService.findById(id);
        bebanService.delete(id);
        return "redirect:/beban/" + beban.getKelompok().getId();
    }
}
