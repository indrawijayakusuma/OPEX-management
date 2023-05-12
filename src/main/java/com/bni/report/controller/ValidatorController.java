package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.User;
import com.bni.report.entities.Validator;
import com.bni.report.repositories.ValidatorRepository;
import com.bni.report.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ValidatorController {

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    ValidatorRepository validatorRepository;

    @GetMapping("/validator")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAll(Model model){
        return paginateGetAll(1, "name", "asc", model);
    }

    @GetMapping("/validator/page/{no}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String paginateGetAll (
            @PathVariable(value = "no") int currPage,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model
    ){
        int pageSize = 9;

        Page<Validator> validators = validatorService.paginateGetALl(currPage, pageSize, sortDirection, sortField, 1);

        List<Validator> validatorList = new ArrayList<>();
        validatorList = validators.getContent();

        model.addAttribute("currentPage", currPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseDirection", sortDirection.equals("asc")?"desc":"asc");

        model.addAttribute("totalPages", validators.getTotalPages());
        model.addAttribute("totalItems", validators.getTotalElements());
        model.addAttribute("validators", validatorList);

        return "validasi1";
    }

    @GetMapping("/validator/addform/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addForm(@PathVariable Integer id, Model model){
        Validator validator = new Validator();
        validator.setBeban(new Beban(id));
        model.addAttribute("validators", validator);
        return "formAddKegiatan";
    }
    @PostMapping("/validator")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String add(Validator validator){
        validatorService.create(validator);
        return "redirect:/kegiatan/" + validator.getBeban().getId();
    }

    @GetMapping("/validator/validate/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String validate(@PathVariable Integer id){
        validatorService.validate(id);
        return "redirect:/validator";
    }
    @GetMapping("/validator/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String formUpdateKegiatan(@PathVariable Integer id, Model model){
        Validator byId = validatorService.findById(id);
        model.addAttribute("kegiatans", byId);
        return "formUpdateValidator";
    }

    @PostMapping("/validator/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(Validator validator){
        validatorService.create(validator);
        return "redirect:/validator";
    }

}
