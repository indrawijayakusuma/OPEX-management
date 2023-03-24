package com.bni.report.controller;

import com.bni.report.entities.Validator;
import com.bni.report.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ValidatorController {

    @Autowired
    private ValidatorService validatorService;

    @GetMapping("/validator")
    public ResponseEntity<List<Validator>> getAll(){
        return ResponseEntity.ok().body(validatorService.getAll());
    }
    @PostMapping("/validator/{id}")
    public ResponseEntity<?> validate(@PathVariable Integer id){
        validatorService.validate(id);
        return ResponseEntity.ok().body("validated");
    }
    @PostMapping("/validator")
    public ResponseEntity<Validator> create(@RequestBody Validator validator){
        return ResponseEntity.ok().body(validatorService.create(validator));
    }
    @GetMapping("/validator/{id}")
    public ResponseEntity<Validator> getById(@PathVariable Integer id){
        return ResponseEntity.ok().body(validatorService.findById(id));
    }
    @DeleteMapping("/validator/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        validatorService.delete(id);
        return ResponseEntity.ok().body("deleted");
    }
    @PutMapping("/validator")
    public ResponseEntity<Validator> edit(@RequestBody Validator validator){
        return ResponseEntity.ok().body(validatorService.edit(validator));
    }

}
