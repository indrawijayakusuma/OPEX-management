package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.MataAnggaran;
import com.bni.report.entities.Program;
import com.bni.report.entities.validators.Validator;
import com.bni.report.entities.validators.ValidatorMataAnggaran;
import com.bni.report.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private ValidatorMataAnggaranService validatorMataAnggaranService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private BebanService bebanService;

    @GetMapping("/validator")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAll(Model model, Authentication authentication) {
        return paginateGetAll(1, "validatorKegiatan", "id", "asc", model, authentication);
    }

    @GetMapping("/validator/page/{no}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String paginateGetAll(
            @PathVariable(value = "no") int currPage,
            @RequestParam(defaultValue = "validatorKegiatan") String list,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model, Authentication authentication
    ) {
        int pageSize = 9;
        String user = authentication.getName();
        Page<Kegiatan> validators = null;
        Page<Program> validatorsProgram = null;
        Page<Beban> validatorsBeban = null;
        Page<ValidatorMataAnggaran> validatorMataAnggarans = null;

        List<Kegiatan> validatorList = null;
        List<Program> validatorProgramList = null;
        List<Beban> validatorsBebanList = null;
        List<ValidatorMataAnggaran> validatorMataAnggaranList = null;

        if (list.equalsIgnoreCase("validatorKegiatan")) {
            validators = validatorService.paginateGetALl(currPage, pageSize, sortDirection, sortField, user);
            validatorList = validators.getContent();
        } else if (list.equalsIgnoreCase("validatorProgram")) {
            validatorsProgram = validatorService.paginateGetALlProgram(currPage, pageSize, sortDirection, sortField, user);
            validatorProgramList = validatorsProgram.getContent();
        } else if (list.equalsIgnoreCase("validatorBeban")) {
            validatorsBeban = validatorService.paginateGetALlBeban(currPage, pageSize, sortDirection, sortField, user);
            validatorsBebanList = validatorsBeban.getContent();
        }

        model.addAttribute("currentPage", currPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseDirection", sortDirection.equals("asc") ? "desc" : "asc");
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
        kegiatanService.findById(id).ifPresent(kegiatan -> {
            kegiatan.setValidate(true);
            kegiatanService.create(kegiatan);
        });
        return "redirect:/validator";
    }

    @GetMapping("/validator/validate/program/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String validateProgram(@PathVariable String id) {
        programService.getByIdOpt(id).ifPresent(program -> {
            program.setValidate(true);
            programService.create(program);
        });
        return "redirect:/validator/page/1?list=validatorProgram";
    }

    @GetMapping("/validator/validate/beban/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String validateBeban(@PathVariable Integer id) {
        bebanService.getById(id).ifPresent(beban -> {
            beban.setValidate(true);
            bebanService.create(beban);
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
        Optional<Kegiatan> byId = kegiatanService.findById(id);
        if (byId.isPresent()) {
            Kegiatan kegiatan = byId.get();
            model.addAttribute("kegiatans", kegiatan);
            return "formUpdateValidator";
        }
        return "redirect:/validator";
    }

    @GetMapping("/validator/update/program/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String formUpdateProgram(@PathVariable String id, Model model) {
        Optional<Program> byId = programService.getByIdOpt(id);
        if (byId.isPresent()) {
            Program program = byId.get();
            model.addAttribute("program", program);
            return "formUpdateValidatorProgram";
        }
        return "redirect:/validator/page/1?list=validatorProgram";
    }

    @GetMapping("/validator/update/beban/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String formUpdateBeban(@PathVariable Integer id, Model model) {
        Optional<Beban> beban = bebanService.getById(id);
        if (beban.isPresent()) {
            Beban program = beban.get();
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
    public String update(Kegiatan validator) {
        validator.setValidate(false);
        kegiatanService.create(validator);
        return "redirect:/validator";
    }

    @PostMapping("/validator/update/program")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateProgram(Program validator) {
        validator.setValidate(false);
        programService.create(validator);
        return "redirect:/validator/page/1?list=validatorProgram";
    }

    @PostMapping("/validator/update/beban")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateBeban(Beban validator) {
        String nomerRekening = mataAnggaranService.getNomerRekening(validator.getName());
        validator.setNomerRekening(nomerRekening);
        validator.setValidate(false);
        bebanService.create(validator);
        return "redirect:/validator/page/1?list=validatorBeban";
    }

    @PostMapping("/validator/update/mataanggaran")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateMataAnggaran(ValidatorMataAnggaran validator) {
        validatorMataAnggaranService.create(validator);
        return "redirect:/validator/page/1?list=validatorMataAnggaran";
    }
}
