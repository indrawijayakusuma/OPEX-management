package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.service.BebanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BebanController {
    @Autowired
    private BebanService bebanService;

    @GetMapping("/beban")
    public ResponseEntity<List<Beban>> getAll(){
        return ResponseEntity.ok().body(bebanService.getAll());
    }
    @PostMapping("/beban")
    public ResponseEntity<Beban> add(@RequestBody Beban beban){
        return ResponseEntity.ok().body(bebanService.create(beban));
    }
    @GetMapping("/beban/{id}")
    public ResponseEntity<Beban> getById(@PathVariable Integer id){
        return ResponseEntity.ok().body(bebanService.findById(id));
    }
    @PutMapping("/beban")
    public ResponseEntity<Beban> edit(@RequestBody Beban beban){
        return ResponseEntity.ok().body(bebanService.edit(beban));
    }
    @DeleteMapping("/beban/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        bebanService.delete(id);
        return ResponseEntity.ok().body("deleted");
    }
}
