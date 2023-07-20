package com.bni.report.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data @NoArgsConstructor @AllArgsConstructor
public class ProgramInputDTO {
    private String name;
    private BigDecimal budget;
    private String pic;
    private String noUsulan;
    private String namaMataAnggaran;
}
