package com.bni.report.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Beban {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "beban_id")
    private Integer id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy="beban", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kegiatan> kegiatan;
    private BigDecimal budget;
    private BigDecimal sisa;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;


    public Beban(String name, BigDecimal budget, Date date) {
        this.name = name;
        this.budget = budget;
        this.date = date;
    }

    public Beban(Integer id){
        this.id = id;
    }

}
