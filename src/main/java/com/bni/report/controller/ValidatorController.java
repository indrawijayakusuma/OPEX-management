package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.MataAnggaran;
import com.bni.report.entities.Program;
import com.bni.report.entities.validators.Validator;
import com.bni.report.entities.validators.ValidatorBeban;
import com.bni.report.entities.validators.ValidatorMataAnggaran;
import com.bni.report.entities.validators.ValidatorProgram;
import com.bni.report.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ValidatorController {

    @Autowired
    private ValidatorService validatorService;
    @Autowired
    private MataAnggaranService mataAnggaranService;
    @Autowired
    private KegiatanService kegiatanService;
    @Autowired
    private ValidatorProgramService validatorProgramService;
    @Autowired
    private ValidatorBebanService validatorBebanService;
    @Autowired
    private ValidatorMataAnggaranService validatorMataAnggaranService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private BebanService bebanService;

    @GetMapping("/validator")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAll(Model model, Authentication authentication) {
        return paginateGetAll(1, "validatorKegiatan", "name", "asc", model, authentication);
    }

    @GetMapping("/validator/page/{no}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String paginateGetAll(
            @PathVariable(value = "no") int currPage,
            @RequestParam(defaultValue = "validatorKegiatan") String list,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model, Authentication authentication
    ) {
        int pageSize = 9;
        String user = authentication.getName();
        Page<Validator> validators = null;
        Page<ValidatorProgram> validatorsProgram = null;
        Page<ValidatorBeban> validatorsBeban = null;
        Page<ValidatorMataAnggaran> validatorMataAnggarans = null;

        List<Validator> validatorList = null;
        List<ValidatorProgram> validatorProgramList = null;
        List<ValidatorBeban> validatorsBebanList = null;
        List<ValidatorMataAnggaran> validatorMataAnggaranList = null;

        if (list.equalsIgnoreCase("validatorKegiatan")) {
            validators = validatorService.paginateGetALl(currPage, pageSize, sortDirection, sortField, user);
            validatorList = validators.getContent();
        } else if (list.equalsIgnoreCase("validatorProgram")) {
            validatorsProgram = validatorProgramService.paginateGetALl(currPage, pageSize, sortDirection, sortField, user);
            validatorProgramList = validatorsProgram.getContent();
        } else if (list.equalsIgnoreCase("validatorBeban")) {
            validatorsBeban = validatorBebanService.paginateGetALl(currPage, pageSize, sortDirection, sortField, user);
            validatorsBebanList = validatorsBeban.getContent();
        } else if (list.equalsIgnoreCase("validatorMataAnggaran")) {
            validatorMataAnggarans = validatorMataAnggaranService.paginateGetALl(currPage, pageSize, sortDirection, sortField, user);
            validatorMataAnggaranList = validatorMataAnggarans.getContent();
        }

        model.addAttribute("currentPage", currPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseDirection", sortDirection.equals("asc") ? "desc" : "asc");
//        model.addAttribute("totalPages", validators.getTotalPages());
//        model.addAttribute("totalItems", validators.getTotalElements());
        model.addAttribute("validators", validatorList);
        model.addAttribute("validatorsProgram", validatorProgramList);
        model.addAttribute("validatorsBebanList", validatorsBebanList);
        model.addAttribute("validatorMataAnggaranList", validatorMataAnggaranList);
        model.addAttribute("listValidasi", list);

        return "validasi1";
    }

    @GetMapping("/validator/validate/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String validate(@PathVariable Integer id) {
        validatorService.findById(id).map(Kegiatan::new).ifPresent(kegiatan -> {
            kegiatanService.create(kegiatan);
            validatorService.delete(id);
        });
        return "redirect:/validator";
    }

    @GetMapping("/validator/validate/program/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String validateProgram(@PathVariable String id) {
        validatorProgramService.findByid(id).map(Program::new).ifPresent(program -> {
            programService.create(program);
            validatorProgramService.delete(id);
        });
        return "redirect:/validator/page/1?list=validatorProgram";
    }

    @GetMapping("/validator/validate/beban/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String validateBeban(@PathVariable Integer id) {
        validatorBebanService.findByid(id).map(Beban::new).ifPresent(beban -> {
            bebanService.create(beban);
            validatorBebanService.delete(id);
        });
        return "redirect:/validator/page/1?list=validatorBeban";
    }

    @GetMapping("/validator/validate/mataanggaran/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String validateMataAnggaran(@PathVariable String id) {
        validatorMataAnggaranService.findByid(id).map(MataAnggaran::new).ifPresent(mataAnggaran -> {
            mataAnggaranService.create(mataAnggaran);
            validatorMataAnggaranService.delete(id);
        });
        return "redirect:/validator/page/1?list=validatorMataAnggaran";
    }

    @GetMapping("/validator/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String formUpdateKegiatan(@PathVariable Integer id, Model model) {
        Optional<Validator> byId = validatorService.findById(id);
        if (byId.isPresent()) {
            Validator validator = byId.get();
            model.addAttribute("kegiatans", validator);
            return "formUpdateValidator";
        }
        return "redirect:/validator";
    }

    @GetMapping("/validator/update/program/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String formUpdateProgram(@PathVariable String id, Model model) {
        Optional<ValidatorProgram> byId = validatorProgramService.findByid(id);
        if (byId.isPresent()) {
            ValidatorProgram program = byId.get();
            model.addAttribute("program", program);
            return "formUpdateValidatorProgram";
        }
        return "redirect:/validator";
    }

    @GetMapping("/validator/update/beban/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String formUpdateBeban(@PathVariable Integer id, Model model) {
        Optional<ValidatorBeban> byId = validatorBebanService.findByid(id);
        if (byId.isPresent()) {
            ValidatorBeban program = byId.get();
            Integer kelompokId = program.getKelompok().getId();
            List<String> all = mataAnggaranService.getAll(kelompokId).stream()
                    .map(MataAnggaran::getMataAnggaran)
                    .toList();
            model.addAttribute("bebans", program);
            model.addAttribute("mataAnggarans", all);
            return "formUpdateValidatorBeban";
        }
        return "redirect:/validator/page/1?list=validatorBeban";
    }

    @GetMapping("/validator/update/mataanggaran/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String formUpdateMataAnggaran(@PathVariable String id, Model model) {
        Optional<ValidatorMataAnggaran> byId = validatorMataAnggaranService.findByid(id);
        if (byId.isPresent()) {
            ValidatorMataAnggaran MataAnggaran = byId.get();
            model.addAttribute("MataAnggaran", MataAnggaran);
            return "formUpdateValidatorMataAnggaran";
        }
        return "redirect:/validator";
    }

    @PostMapping("/validator/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(Validator validator) {
        validatorService.create(validator);
        return "redirect:/validator";
    }

    @PostMapping("/validator/update/program")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateProgram(ValidatorProgram validator) {
        validatorProgramService.create(validator);
        return "redirect:/validator/page/1?list=validatorProgram";
    }

    @PostMapping("/validator/update/beban")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateBeban(ValidatorBeban validator) {
        String nomerRekening = mataAnggaranService.getNomerRekening(validator.getName());
        validator.setNomerRekening(nomerRekening);
        validatorBebanService.create(validator);
        return "redirect:/validator/page/1?list=validatorBeban";
    }

    @PostMapping("/validator/update/mataanggaran")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateMataAnggaran(ValidatorMataAnggaran validator) {
        validatorMataAnggaranService.create(validator);
        return "redirect:/validator/page/1?list=validatorMataAnggaran";
    }
}
