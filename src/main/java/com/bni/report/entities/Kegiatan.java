package com.bni.report.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Kegiatan {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name="Beban_id", nullable=false)
    private Beban beban;
    private String cat;
    private String pic;
    private BigDecimal nominal;
    private Date date;

    public Kegiatan(Validator validator) {
        this.name =   validator.getName();
        this.beban =   validator.getBeban();
        this.cat =   validator.getCat();
        this.pic =   validator.getPic();
        this.nominal =   validator.getNominal();
        this.date =   validator.getDate();
    }

    public Kegiatan(String name, Beban beban, String cat, String pic, BigDecimal nominal, Date date) {
        this.name = name;
        this.beban = beban;
        this.cat = cat;
        this.pic = pic;
        this.nominal = nominal;
        this.date = date;
    }
}
