package com.bni.report.controller;
import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.Program;
import com.bni.report.service.BebanService;
import com.bni.report.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProgramController {

    @Autowired
    private ProgramService programService;
    @Autowired
    private BebanService bebanService;

    @GetMapping("/program/{id}")
    public String getAll(@PathVariable Integer id, Model model){
        return paginateGetAll(null, id,1, "name", "asc", model);
    }

    @GetMapping("/program/page/{id}/{no}")
    public String paginateGetAll(
            @RequestParam(required = false) String keyword,
            @PathVariable(value = "id") Integer IdBeban,
            @PathVariable(value = "no") int currPage,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model
    ){
        int pageSize = 15;
        Page<Program> programPage = null;
        if(keyword == null){
            programPage = programService.paginateGetAll(currPage,pageSize,sortField,sortDirection,IdBeban);
        }else {
            programPage = programService.paginateSearchingGetAll(currPage,pageSize,sortField,sortDirection,keyword,IdBeban);
        }

        List<Program> programPageContent = programPage.getContent();
        Beban beban = bebanService.findById(IdBeban);
        Integer kelompokid = beban.getKelompok().getId();
        String namaBeban = beban.getName();
        String nameKelompok = beban.getKelompok().getName();

        model.addAttribute("currentPage", currPage);
        model.addAttribute("totalPages", programPage.getTotalPages());
        model.addAttribute("totalItems", programPage.getTotalElements());
        model.addAttribute("programs", programPageContent);
        model.addAttribute("kelompokId", kelompokid);
        model.addAttribute("namaBeban", namaBeban);
        model.addAttribute("nameKelompok", nameKelompok);
        model.addAttribute("IdBeban", IdBeban);

        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("sortField", sortField);
        model.addAttribute("reverseDirection", sortDirection.equals("asc")?"desc":"asc");
        model.addAttribute("programAdd", new Program());

        return "listProgram";
    }
    @PostMapping("/program/{idBeban}")
    public String add(@PathVariable Integer idBeban, Program program){
        program.setBeban(new Beban(idBeban));
        programService.create(program);
        return "redirect:/program/" + idBeban;
    }
    @GetMapping("/program/update/{id}")
    public String formUpdateKegiatan(@PathVariable String id, Model model){
        Program byId = programService.getById(id);
        Integer kelompokid = byId.getBeban().getKelompok().getId();
        model.addAttribute("kelompokId", kelompokid);
        model.addAttribute("program", byId);
        return "formUpdateProgram";
    }

    @PostMapping("/program")
    public String update(Program program){
        programService.create(program);
        return "redirect:/program/" + program.getBeban().getId();
    }

    @GetMapping("program/delete/{id}")
    public String delete(@PathVariable String id){
        Integer idBeban = programService.getById(id).getBeban().getId();
        programService.delete(id);
        return "redirect:/program/" + idBeban;
    }
}
