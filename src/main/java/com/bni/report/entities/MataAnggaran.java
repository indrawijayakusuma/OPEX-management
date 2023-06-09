package com.bni.report.entities;

import com.bni.report.validation.UniqueField;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MataAnggaran {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @UniqueField(message = "Nomer rekening sudah terdaftar")
    @NotBlank
    @Column(unique = true)
    private String nomerRekening;
    @UniqueField
    @NotBlank
    @Column(unique = true)
    private String mataAnggaran;
    @ManyToOne
    @JoinColumn(name="Kelompok_id", nullable=false)
    private Kelompok kelompok;

}