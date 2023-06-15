package com.bni.report.entities;

import com.bni.report.service.BebanService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.List;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Program {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private BigDecimal budget;
    private String pic;
    private String noUsulan;
    @OneToMany(mappedBy="program", cascade = CascadeType.ALL)
    private List<Kegiatan> kegiatan;
    @ManyToOne
    @JoinColumn(name="Beban_id", nullable=false)
    private Beban beban;

    public Program(String id) {
        this.id = id;
    }
}
