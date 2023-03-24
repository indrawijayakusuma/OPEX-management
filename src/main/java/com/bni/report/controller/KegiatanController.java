package com.bni.report.controller;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.service.KegiatanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class KegiatanController {
    @Autowired
    private KegiatanService kegiatanService;
    @GetMapping("/kegiatan")
    public ResponseEntity<List<Kegiatan>> getAll(){
        return ResponseEntity.ok().body(kegiatanService.getAll());
    }
    @GetMapping("/kegiatan/{id}")
    public ResponseEntity<Kegiatan> getById(@PathVariable Integer id){
        return ResponseEntity.ok().body(kegiatanService.findById(id));
    }
    @PostMapping("/kegiatan")
    public ResponseEntity<Kegiatan> add(@RequestBody Kegiatan kegiatan){
        return ResponseEntity.ok().body(kegiatanService.create(kegiatan));
    }
    @PutMapping("/kegiatan")
    public ResponseEntity<Kegiatan> edit(@RequestBody Kegiatan kegiatan){
        return ResponseEntity.ok().body(kegiatanService.edit(kegiatan));
    }
    @DeleteMapping("/kegiatan/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        kegiatanService.delete(id);
        return ResponseEntity.ok().body("deleted");
    }
    @GetMapping("/kegiatan/beban/{id}")
    public ResponseEntity<List<Kegiatan>> findByBeban(@PathVariable Integer id){
        return ResponseEntity.ok().body(kegiatanService.findByBebanId(id));
    }

}
