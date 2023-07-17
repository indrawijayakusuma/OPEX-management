package com.bni.report.entities;

import com.bni.report.entities.validators.Validator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kegiatan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "Program_id", nullable = false)
    private Program program;
    private BigDecimal budget;
    private BigDecimal realisasi;
    private BigDecimal sisa;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public Kegiatan(Validator validator) {
        this.budget = validator.getBudget();
        this.realisasi = validator.getRealisasi();
        this.sisa = validator.getSisa();
        this.program = validator.getProgram();
        this.date = validator.getDate();
    }

    public Kegiatan(Integer id, Program program, BigDecimal realisasi, Date date) {
        this.id = id;
        this.program = program;
        this.realisasi = realisasi;
        this.date = date;
    }
}
