package com.bni.report.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Beban {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "beban_id")
    private Integer id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy="beban")
    private List<Kegiatan> kegiatan;
    private BigDecimal budget;
    private BigDecimal sisa;
    private Date date;

}
