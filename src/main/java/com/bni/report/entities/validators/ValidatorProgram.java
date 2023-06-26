package com.bni.report.entities.validators;

import com.bni.report.entities.Beban;
import com.bni.report.entities.Kegiatan;
import com.bni.report.entities.Program;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import java.math.BigDecimal;
@Entity @Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidatorProgram {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private BigDecimal budget;
    private String pic;
    private String noUsulan;
    @ManyToOne
    @JoinColumn(name="Beban_id", nullable=false)
    private Beban beban;

    public ValidatorProgram(Program program) {
        this.id = program.getId();
        this.name = program.getName();
        this.budget = program.getBudget();
        this.pic = program.getPic();
        this.noUsulan = program.getNoUsulan();
        this.beban = program.getBeban();
    }
}
