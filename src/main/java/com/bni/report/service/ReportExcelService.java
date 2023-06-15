package com.bni.report.service;

import com.bni.report.entities.Kegiatan;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ReportExcelService {

    @Autowired
    private KegiatanService kegiatanService;

    @Autowired
    private BebanService bebanService;

    public void generateHeader(XSSFWorkbook workbook, XSSFSheet sheet){
        XSSFFont font = workbook.createFont();
        font.setBold(true);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        border(cellStyle);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(font);

        XSSFRow row = sheet.createRow(1);
        row.setHeight((short) 500);
        XSSFCell cellnumber = row.createCell(0);
        cellnumber.setCellValue("No");
        cellnumber.setCellStyle(cellStyle);
        XSSFCell cellDate = row.createCell(1);
        cellDate.setCellValue("Date");
        cellDate.setCellStyle(cellStyle);
        XSSFCell cell = row.createCell(2);
        cell.setCellValue("Realisasi");
        cell.setCellStyle(cellStyle);
        XSSFCell cellCategory = row.createCell(3);
        cellCategory.setCellValue("Category");
        cellCategory.setCellStyle(cellStyle);
        XSSFCell cellPic = row.createCell(4);
        cellPic.setCellValue("Pic");
        cellPic.setCellStyle(cellStyle);
        XSSFCell cellNominal = row.createCell(5);
        cellNominal.setCellValue("Nominal");
        cellNominal.setCellStyle(cellStyle);
    }

    public void border(XSSFCellStyle cellStyle){
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
    }

    public void generateDataList(XSSFWorkbook workbook, XSSFSheet sheet, List<Kegiatan> kegiatans){
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        border(cellStyle);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);

        XSSFCellStyle cellStyleRight = workbook.createCellStyle();
        border(cellStyleRight);
        cellStyleRight.setAlignment(HorizontalAlignment.LEFT);
        cellStyleRight.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleRight.setWrapText(true);

        CreationHelper creationHelper = workbook.getCreationHelper();
        XSSFCellStyle cellStyleLeft = workbook.createCellStyle();
        border(cellStyleLeft);
        cellStyleLeft.setAlignment(HorizontalAlignment.RIGHT);
        cellStyleLeft.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleLeft.setDataFormat(creationHelper.createDataFormat().getFormat("#,##0"));

        XSSFCellStyle cellDateStyle = workbook.createCellStyle();
        cellDateStyle.cloneStyleFrom(cellStyle);
        cellDateStyle.setDataFormat((short)14);

        AtomicInteger counter = new AtomicInteger(2);
        AtomicInteger number = new AtomicInteger(1);
        kegiatans.stream().forEach(kegiatan -> {
            XSSFRow row = sheet.createRow(counter.get());
            row.setHeight((short)-1);

            XSSFCell cellNumber = row.createCell(0);
            cellNumber.setCellValue(number.doubleValue());
            cellNumber.setCellStyle(cellStyle);

            XSSFCell cellDate = row.createCell(1);
            cellDate.setCellValue(kegiatan.getDate());
            cellDate.setCellStyle(cellDateStyle);

//            XSSFCell cell = row.createCell(2);
//            cell.setCellValue(kegiatan.getName());
//            cell.setCellStyle(cellStyleRight);

//            XSSFCell cellCategory = row.createCell(3);
//            cellCategory.setCellValue(kegiatan.getCat());
//            cellCategory.setCellStyle(cellStyle);
//
//            XSSFCell cellPic = row.createCell(4);
//            cellPic.setCellValue(kegiatan.getPic());
//            cellPic.setCellStyle(cellStyle);
//
//            XSSFCell cellNominal = row.createCell(5);
//            cellNominal.setCellValue(kegiatan.getNominal().doubleValue());
//            cellNominal.setCellStyle(cellStyleLeft);

            counter.getAndIncrement();
            number.getAndIncrement();
        });
    }

    public void generateFooter(XSSFWorkbook workbook, XSSFSheet sheet, int size, BigDecimal sisa){
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        XSSFCellStyle numberFormat = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        numberFormat.cloneStyleFrom(cellStyle);
        numberFormat.setDataFormat(creationHelper.createDataFormat().getFormat("#,##0"));

        CellRangeAddress cellAddresses = new CellRangeAddress(size + 1, size + 1, 0, 2);
        CellRangeAddress cellAddresses1 = new CellRangeAddress(size + 1, size + 1, 3, 5);
        sheet.addMergedRegion(cellAddresses);
        sheet.addMergedRegion(cellAddresses1);

        XSSFRow row = sheet.createRow(size+1);
        row.setHeight((short) 500);
        XSSFCell cellnumber = row.createCell(0);
        cellnumber.setCellValue("Sisa");
        cellnumber.setCellStyle(cellStyle);
        XSSFCell cellnumber2 = row.createCell(1);
        cellnumber2.setCellValue("");
        cellnumber2.setCellStyle(cellStyle);
        XSSFCell cellnumber1 = row.createCell(2);
        cellnumber1.setCellValue("");
        cellnumber1.setCellStyle(cellStyle);
        XSSFCell cellnumber3 = row.createCell(3);
        cellnumber3.setCellValue(sisa.doubleValue());
        cellnumber3.setCellStyle(numberFormat);
        XSSFCell cellnumber4 = row.createCell(4);
        cellnumber4.setCellValue("");
        cellnumber4.setCellStyle(cellStyle);
        XSSFCell cellnumber5 = row.createCell(5);
        cellnumber5.setCellValue("");
        cellnumber5.setCellStyle(cellStyle);
    }

    public void generateExcel(HttpServletResponse response, String id) throws IOException {
        List<Kegiatan> kegiatans = kegiatanService.getByProgramId(id);
        int size = kegiatans.size();
        BigDecimal sisa = bebanService.findById(1).getSisa();

        XSSFWorkbook workbook =  new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("TEST ");
        sheet.setColumnWidth(5,5000);
        sheet.setColumnWidth(2,9000);
        sheet.setColumnWidth(0,1200);
        generateHeader(workbook, sheet);
        generateDataList(workbook, sheet, kegiatans);
        generateFooter(workbook, sheet, size, sisa);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
