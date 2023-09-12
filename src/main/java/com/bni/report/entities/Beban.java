package com.bni.report.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
//budget mata anggaran
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Beban {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nomerRekening;
    private String name;
    @ManyToOne
    @JoinColumn(name = "Kelompok_id", nullable = false)
    private Kelompok kelompok;
    @OneToMany(mappedBy = "beban", cascade = CascadeType.ALL)
    private List<Program> program;
    private BigDecimal budget;
    private BigDecimal realisasi;
    private BigDecimal sisa;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Boolean validate;

    public Beban(Integer id, String nomerRekening, String name, Kelompok kelompok, BigDecimal budget, Date date) {
        this.id = id;
        this.nomerRekening = nomerRekening;
        this.name = name;
        this.kelompok = kelompok;
        this.budget = budget;
        this.date = date;
    }


    public Beban(Integer id) {
        this.id = id;
    }

}
