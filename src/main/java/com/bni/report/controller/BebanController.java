package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kelompok;
import com.bni.report.entities.MataAnggaran;
import com.bni.report.service.BebanService;
import com.bni.report.service.KelompokService;
import com.bni.report.service.MataAnggaranService;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller @Slf4j
public class BebanController {
    @Autowired
    private BebanService bebanService;
    @Autowired
    private KelompokService kelompokService;

    @Autowired
    private MataAnggaranService mataAnggaranService;

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

//    @PostMapping("/beban")
//    public String add(Beban beban){
//        beban.setKelompok(new Kelompok(1));
//        bebanService.create(beban);
//        return "redirect:/beban/" + beban.getId();
//    }

    @PostMapping("/beban/{kelompokId}")
    public String add(Beban beban, @PathVariable Integer kelompokId){
        String nomerRekening = mataAnggaranService.getNomerRekening(beban.getName());
        beban.setKelompok(new Kelompok(kelompokId));
        beban.setNomerRekening(nomerRekening);
        bebanService.create(beban);
        return "redirect:/beban/" + kelompokId;
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
