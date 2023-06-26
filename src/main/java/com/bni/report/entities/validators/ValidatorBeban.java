package com.bni.report.entities.validators;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kelompok;
import com.bni.report.entities.Program;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidatorBeban {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nomerRekening;
    private String name;
    @ManyToOne
    @JoinColumn(name="Kelompok_id", nullable=false)
    private Kelompok kelompok;
    private BigDecimal budget;
    private BigDecimal realisasi;
    private BigDecimal sisa;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public ValidatorBeban(Beban beban){
        this.id = beban.getId();
        this.nomerRekening = beban.getNomerRekening();
        this.name = beban.getName();
        this.kelompok = beban.getKelompok();
        this.budget = beban.getBudget();
        this.realisasi = beban.getRealisasi();
        this.sisa = beban.getSisa();
        this.date = beban.getDate();
    }
}
