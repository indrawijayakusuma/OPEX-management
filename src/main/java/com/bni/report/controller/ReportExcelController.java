package com.bni.report.controller;

import com.bni.report.service.ReportExcelService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class ReportExcelController {

    @Autowired
    private ReportExcelService reportExcelService;

    @GetMapping("/excel/{id}")
    public void generateExcel(@PathVariable String id, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=opex.xlsx";

        response.setHeader(headerKey, headerValue);
        reportExcelService.generateExcel(response, id);
    }
}
