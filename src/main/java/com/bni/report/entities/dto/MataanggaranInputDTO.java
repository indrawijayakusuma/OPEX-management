package com.bni.report.entities.dto;

import com.bni.report.entities.Kelompok;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MataanggaranInputDTO {
    private String mataAnggaranEdit;
    private String nomerRekening;
    private String mataAnggaran;
    private Kelompok kelompok;
}
