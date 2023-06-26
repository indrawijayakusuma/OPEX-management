package com.bni.report.entities;

import com.bni.report.entities.validators.ValidatorBeban;
import com.bni.report.entities.validators.ValidatorProgram;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Beban {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nomerRekening;
    private String name;
    @ManyToOne
    @JoinColumn(name="Kelompok_id", nullable=false)
    private Kelompok kelompok;
    @OneToMany(mappedBy="beban", cascade = CascadeType.ALL)
    private List<Program> program;
    @OneToMany(mappedBy="beban", cascade = CascadeType.ALL)
    private List<ValidatorProgram> validatorPrograms;
    private BigDecimal budget;
    private BigDecimal realisasi;
    private BigDecimal sisa;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public Beban(String nomerRekening, String name, BigDecimal budget, Date date, Kelompok kelompok) {
        this.nomerRekening = nomerRekening;
        this.name = name;
        this.budget = budget;
        this.date = date;
        this.kelompok = kelompok;
    }

    public Beban(ValidatorBeban beban){
        this.id = beban.getId();
        this.nomerRekening = beban.getNomerRekening();
        this.name = beban.getName();
        this.kelompok = beban.getKelompok();
        this.budget = beban.getBudget();
        this.realisasi = beban.getRealisasi();
        this.sisa = beban.getSisa();
        this.date = beban.getDate();
    }

    public Beban(Integer id){
        this.id = id;
    }

}
