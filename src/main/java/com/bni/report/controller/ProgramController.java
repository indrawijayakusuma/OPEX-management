package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Program;
import com.bni.report.entities.validators.ValidatorProgram;
import com.bni.report.service.BebanService;
import com.bni.report.service.ProgramService;
import com.bni.report.service.ValidatorProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProgramController {

    @Autowired
    private ProgramService programService;
    @Autowired
    private BebanService bebanService;
    @Autowired
    private ValidatorProgramService validatorProgramService;

    @GetMapping("/program/{id}")
    public String getAll(@PathVariable Integer id, Model model) {
        return paginateGetAll(null, id, 1, "name", "asc", model);
    }

    @GetMapping("/program/page/{id}/{no}")
    public String paginateGetAll(
            @RequestParam(required = false) String keyword,
            @PathVariable(value = "id") Integer IdBeban,
            @PathVariable(value = "no") int currPage,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model
    ) {
        int pageSize = 15;
        Page<Program> programPage = null;
        if (keyword == null) {
            programPage = programService.paginateGetAll(currPage, pageSize, sortField, sortDirection, IdBeban);
        } else {
            programPage = programService.paginateSearchingGetAll(currPage, pageSize, sortField, sortDirection, keyword, IdBeban);
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
        model.addAttribute("reverseDirection", sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("programAdd", new Program());

        return "listProgram";
    }

    @PostMapping("/program/{idBeban}")
    public String add(@PathVariable Integer idBeban, Program program) {
        program.setBeban(new Beban(idBeban));
        Optional.of(program)
                .map(ValidatorProgram::new)
                .ifPresent(program1 -> {
                    validatorProgramService.create(program1);
                });
        return "redirect:/program/" + idBeban;
    }

    @GetMapping("/program/update/{id}")
    public String formUpdateKegiatan(@PathVariable String id, Model model) {
        Program byId = programService.getById(id);
        Integer bebanId = byId.getBeban().getId();
        model.addAttribute("bebanId", bebanId);
        model.addAttribute("program", byId);
        return "formUpdateProgram";
    }

    @PostMapping("/program")
    public String update(Program program) {
        ValidatorProgram validatorProgram = Optional.of(program).map(ValidatorProgram::new).get();
        validatorProgramService.create(validatorProgram);
        programService.delete(program.getId());
        return "redirect:/program/" + program.getBeban().getId();
    }

    @GetMapping("program/delete/{id}")
    public String delete(@PathVariable String id) {
        Integer idBeban = programService.getById(id).getBeban().getId();
        programService.delete(id);
        return "redirect:/program/" + idBeban;
    }
}
