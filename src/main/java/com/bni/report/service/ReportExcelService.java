package com.bni.report.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReportExcelService {

    public void generateHeader(XSSFWorkbook workbook, XSSFSheet sheet){
        XSSFFont font = workbook.createFont();
        font.setBold(true);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(font);

        XSSFRow row = sheet.createRow(1);
        row.setHeight((short) 500);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("Realisasi");
        cell.setCellStyle(cellStyle);
        XSSFCell cellCategory = row.createCell(1);
        cellCategory.setCellValue("Category");
        cellCategory.setCellStyle(cellStyle);
        XSSFCell cellPic = row.createCell(2);
        cellPic.setCellValue("Pic");
        cellPic.setCellStyle(cellStyle);
        XSSFCell cellNominal = row.createCell(3);
        cellNominal.setCellValue("Nominal");
        cellNominal.setCellStyle(cellStyle);
        XSSFCell cellDate = row.createCell(4);
        cellDate.setCellValue("Date");
        cellDate.setCellStyle(cellStyle);
    }
    public void generateDataList(XSSFWorkbook workbook, XSSFSheet sheet){
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);

        XSSFRow row = sheet.createRow(2);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("kegiatan lomba");
        cell.setCellStyle(cellStyle);
        XSSFCell cellCategory = row.createCell(1);
        cellCategory.setCellValue("Category");
        cellCategory.setCellStyle(cellStyle);
        XSSFCell cellPic = row.createCell(2);
        cellPic.setCellValue("sana");
        cellPic.setCellStyle(cellStyle);
        XSSFCell cellNominal = row.createCell(3);
        cellNominal.setCellValue("10.000.000");
        cellNominal.setCellStyle(cellStyle);
        XSSFCell cellDate = row.createCell(4);
        cellDate.setCellValue("12/10/2020");
        cellDate.setCellStyle(cellStyle);
    }

    public void generateFooter(){

    }

    public void generateExcel(HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook =  new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("TEST ");
        sheet.setDefaultColumnWidth(15);

        generateHeader(workbook, sheet);
        generateDataList(workbook,sheet);
        generateFooter();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
