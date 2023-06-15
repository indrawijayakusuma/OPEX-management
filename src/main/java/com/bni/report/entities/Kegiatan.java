package com.bni.report.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Kegiatan {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="Program_id", nullable=false)
    private Program program;
    private BigDecimal budget;
    private BigDecimal realisasi;
    private BigDecimal sisa;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public Kegiatan(Validator validator) {
        this.date =   validator.getDate();
    }

    public Kegiatan(String name, Beban beban, String cat, String pic, BigDecimal nominal, Date date) {
        this.date = date;
    }
}
