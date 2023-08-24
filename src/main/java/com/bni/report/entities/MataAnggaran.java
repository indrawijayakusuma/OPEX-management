package com.bni.report.entities;

import com.bni.report.entities.dto.MataanggaranInputDTO;
import com.bni.report.entities.validators.ValidatorMataAnggaran;
import com.bni.report.validation.UniqueField;
import com.bni.report.validation.UniqueMataanggaaran;
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
    @UniqueMataanggaaran(message = "Mata Anggaran sudah terdaftar")
    @NotBlank
    @Column(unique = true)
    private String mataAnggaran;
    @ManyToOne
    @JoinColumn(name = "Kelompok_id", nullable = false)
    private Kelompok kelompok;

    public MataAnggaran(ValidatorMataAnggaran validatorMataAnggaran) {
        this.id = validatorMataAnggaran.getId();
        this.nomerRekening = validatorMataAnggaran.getNomerRekening();
        this.mataAnggaran = validatorMataAnggaran.getMataAnggaran();
        this.kelompok = validatorMataAnggaran.getKelompok();
    }


    public MataAnggaran(String nomerRekening, String mataAnggaran, Kelompok kelompok) {
        this.nomerRekening = nomerRekening;
        this.mataAnggaran = mataAnggaran;
        this.kelompok = kelompok;
    }
}
