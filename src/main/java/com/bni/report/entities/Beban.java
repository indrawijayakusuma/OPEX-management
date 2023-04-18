package com.bni.report.entities;

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
    @Column(name = "beban_id")
    private Integer id;
    private String nomerRekening;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy="beban", cascade = CascadeType.ALL)
    private List<Kegiatan> kegiatan;
    @ManyToOne
    @JoinColumn(name="Kelompok_id", nullable=false)
    private Kelompok kelompok;
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
    public Beban(Integer id){
        this.id = id;
    }

}
