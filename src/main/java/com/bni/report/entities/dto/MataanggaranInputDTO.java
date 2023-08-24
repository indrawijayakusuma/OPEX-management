package com.bni.report.entities.dto;

import com.bni.report.entities.Kelompok;
import com.bni.report.validation.UniqueField;
import com.bni.report.validation.UniqueMataanggaaran;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MataanggaranInputDTO {
    private String mataAnggaranEdit;
    @UniqueField(message = "Nomer rekening sudah terdaftar")
    @NotBlank
    @Column(unique = true)
    private String nomerRekening;
    @UniqueMataanggaaran
    @NotBlank
    @Column(unique = true)
    private String mataAnggaran;
}
