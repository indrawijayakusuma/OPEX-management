package com.bni.report.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Kelompok {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "kelompok", cascade = CascadeType.ALL)
    private List<Beban> beban;

    @OneToMany(mappedBy = "kelompok", cascade = CascadeType.ALL)
    private List<MataAnggaran> mataAnggaran;

    public Kelompok(String name) {
        this.name = name;
    }
    public Kelompok(Integer id) {
        this.id = id;
    }
}
