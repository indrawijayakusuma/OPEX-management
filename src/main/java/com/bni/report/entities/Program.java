package com.bni.report.entities;

import com.bni.report.entities.dto.ProgramInputDTO;
import com.bni.report.entities.validators.ValidatorProgram;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Program {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private BigDecimal budget;
    private String pic;
    private String noUsulan;
    private BigDecimal realisasi;
    private BigDecimal sisa;
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Kegiatan> kegiatan;
    @ManyToOne
    @JoinColumn(name = "Beban_id", nullable = false)
    private Beban beban;

    public Program(String id) {
        this.id = id;
    }

    public Program(String id, String name, BigDecimal budget, String pic, String noUsulan, Beban beban) {
        this.id = id;
        this.name = name;
        this.budget = budget;
        this.pic = pic;
        this.noUsulan = noUsulan;
        this.beban = beban;
    }

    public Program(ValidatorProgram program) {
        this.id = program.getId();
        this.name = program.getName();
        this.budget = program.getBudget();
        this.pic = program.getPic();
        this.noUsulan = program.getNoUsulan();
        this.beban = program.getBeban();
    }
}
