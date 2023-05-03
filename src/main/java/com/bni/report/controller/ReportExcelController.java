package com.bni.report.controller;

import com.bni.report.service.ReportExcelService;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class ReportExcelController {

    @Autowired
    private ReportExcelService reportExcelService;

    @GetMapping("/excel")
    public void generateExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey ="Content-Disposition";
        String headerValue = "attachment;filename=test.xlsx";

        response.setHeader(headerKey,headerValue);
        reportExcelService.generateExcel(response);
    }
}
