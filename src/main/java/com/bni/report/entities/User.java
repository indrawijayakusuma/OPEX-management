package com.bni.report.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String password;
    private String roles;
    @ManyToOne
    @JoinColumn(name = "Kelompok_id", nullable = false)
    private Kelompok kelompok;
}
