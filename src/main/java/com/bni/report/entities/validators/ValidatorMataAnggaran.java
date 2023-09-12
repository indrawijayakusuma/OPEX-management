package com.bni.report.entities.validators;

import com.bni.report.entities.Kelompok;
import com.bni.report.entities.MataAnggaran;
import com.bni.report.validation.UniqueField;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidatorMataAnggaran {
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
    @JoinColumn(name = "Kelompok_id", nullable = false)
    private Kelompok kelompok;


    public ValidatorMataAnggaran(MataAnggaran anggaran) {
        this.id = anggaran.getId();
        this.nomerRekening = anggaran.getNomerRekening();
        this.mataAnggaran = anggaran.getMataAnggaran();
        this.kelompok = anggaran.getKelompok();
    }
}
