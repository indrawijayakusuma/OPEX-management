package com.bni.report.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Validator {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name="Beban_id", nullable=false)
    private Beban beban;
    private String cat;
    private String pic;
    private BigDecimal nominal;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
