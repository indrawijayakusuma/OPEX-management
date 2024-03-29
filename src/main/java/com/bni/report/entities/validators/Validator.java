package com.bni.report.entities.validators;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.Program;
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
public class Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String keterangan;
    @ManyToOne
    @JoinColumn(name = "Program_id", nullable = false)
    private Program program;
    private BigDecimal budget;
    private BigDecimal realisasi;
    private BigDecimal sisa;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;


    public Validator(String keterangan, BigDecimal budget, BigDecimal realisasi, BigDecimal sisa, Date date) {
        this.budget = budget;
        this.realisasi = realisasi;
        this.sisa = sisa;
        this.date = date;
        this.keterangan = keterangan;
    }
}
