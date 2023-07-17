package com.bni.report.service;

import com.bni.report.entities.Kegiatan;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ReportExcelService {

    @Autowired
    private KegiatanService kegiatanService;

    @Autowired
    private BebanService bebanService;

    private void generateTitle(XSSFWorkbook workbook, XSSFSheet sheet,Kegiatan kegiatan) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setFont(font);
        XSSFCellStyle numberFormat = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        numberFormat.cloneStyleFrom(cellStyle);
        numberFormat.setDataFormat(creationHelper.createDataFormat().getFormat("_-[$Rp-id-ID]* #,##0_-;-[$Rp-id-ID]* #,##0_-;_-[$Rp-id-ID]* \"-\"_-;_-@_-"));

        XSSFRow row = sheet.createRow(0);
        XSSFCell mataAnggaran = row.createCell(1);
        mataAnggaran.setCellValue("MATA ANGGARAN");
        mataAnggaran.setCellStyle(cellStyle);
        XSSFCell cellNumber = row.createCell(3);
        cellNumber.setCellValue(kegiatan.getProgram().getBeban().getName());
        cellNumber.setCellStyle(cellStyle);

        XSSFRow row1 = sheet.createRow(1);
        XSSFCell cellNumber1 = row1.createCell(1);
        cellNumber1.setCellValue("BUDGET");
        cellNumber1.setCellStyle(cellStyle);
        XSSFCell budget = row1.createCell(3);
        budget.setCellValue(String.valueOf(kegiatan.getProgram().getBeban().getBudget()));
        budget.setCellStyle(numberFormat);

        XSSFRow row2 = sheet.createRow(2);
        XSSFCell cellKelompok = row2.createCell(1);
        cellKelompok.setCellValue("KELOMPOK");
        cellKelompok.setCellStyle(cellStyle);
        XSSFCell cellKelompok1 = row2.createCell(3);
        cellKelompok1.setCellValue(kegiatan.getProgram().getBeban().getKelompok().getName());
        cellKelompok1.setCellStyle(cellStyle);

        XSSFRow row3 = sheet.createRow(3);
        XSSFCell cellKodeUnit= row3.createCell(1);
        cellKodeUnit.setCellValue("KODE UNIT");
        cellKodeUnit.setCellStyle(cellStyle);
        XSSFCell cellKodeUnit1 = row3.createCell(3);
        cellKodeUnit1.setCellValue("324");
        cellKodeUnit1.setCellStyle(cellStyle);

        XSSFRow row4 = sheet.createRow(4);
        XSSFCell cellNomer = row4.createCell(1);
        cellNomer.setCellValue("NOMER REKENING");
        cellNomer.setCellStyle(cellStyle);
        XSSFCell cellNomer1 = row4.createCell(3);
        cellNomer1.setCellValue(kegiatan.getProgram().getBeban().getNomerRekening());
        cellNomer1.setCellStyle(cellStyle);
    }

    public void generateHeader(XSSFWorkbook workbook, XSSFSheet sheet) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        border(cellStyle);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(font);

        XSSFRow row = sheet.createRow(5);
        row.setHeight((short) 500);

        XSSFCell cellnumber = row.createCell(0);
        cellnumber.setCellValue("No");
        cellnumber.setCellStyle(cellStyle);

        XSSFCell cellMataAnggaran = row.createCell(1);
        cellMataAnggaran.setCellValue("Nama Program");
        cellMataAnggaran.setCellStyle(cellStyle);

        XSSFCell cellGap = row.createCell(2);
        cellGap.setCellValue("");
        cellGap.setCellStyle(cellStyle);

        XSSFCell cell = row.createCell(3);
        cell.setCellValue("No.Usulan");
        cell.setCellStyle(cellStyle);

        XSSFCell BudgetProgram = row.createCell(4);
        BudgetProgram.setCellValue("Budget program");
        BudgetProgram.setCellStyle(cellStyle);

        XSSFCell cellRealisasi = row.createCell(5);
        cellRealisasi.setCellValue("Realisasi Program");
        cellRealisasi.setCellStyle(cellStyle);

        XSSFCell cellNominal = row.createCell(6);
        cellNominal.setCellValue("Sisa Budget");
        cellNominal.setCellStyle(cellStyle);

        XSSFCell cellPic = row.createCell(7);
        cellPic.setCellValue("Pic");
        cellPic.setCellStyle(cellStyle);

        XSSFCell cellDate = row.createCell(8);
        cellDate.setCellValue("Date");
        cellDate.setCellStyle(cellStyle);
    }

    public void border(XSSFCellStyle cellStyle) {
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
    }

    public void generateDataList(XSSFWorkbook workbook, XSSFSheet sheet, List<Kegiatan> kegiatans) {
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
        cellStyleLeft.setDataFormat(creationHelper.createDataFormat().getFormat("_-[$Rp-id-ID]* #,##0_-;-[$Rp-id-ID]* #,##0_-;_-[$Rp-id-ID]* \"-\"_-;_-@_-"));

        XSSFCellStyle cellDateStyle = workbook.createCellStyle();
        cellDateStyle.cloneStyleFrom(cellStyle);
        cellDateStyle.setDataFormat((short) 14);

        AtomicInteger counter = new AtomicInteger(6);
        AtomicInteger number = new AtomicInteger(1);
        kegiatans.stream().forEach(kegiatan -> {
            XSSFRow row = sheet.createRow(counter.get());
            row.setHeight((short) -1);

            XSSFCell cellNumber = row.createCell(0);
            cellNumber.setCellValue(number.doubleValue());
            cellNumber.setCellStyle(cellStyle);

            XSSFCell cellName = row.createCell(1);
            cellName.setCellValue(kegiatan.getProgram().getName());
            cellName.setCellStyle(cellDateStyle);

            XSSFCell cellGap = row.createCell(2);
            cellGap.setCellValue("");
            cellGap.setCellStyle(cellStyle);

            XSSFCell cell = row.createCell(3);
            cell.setCellValue(kegiatan.getProgram().getNoUsulan());
            cell.setCellStyle(cellStyle);

            XSSFCell cellBudget = row.createCell(4);
            cellBudget.setCellValue(kegiatan.getBudget().doubleValue());
            cellBudget.setCellStyle(cellStyleLeft);

            XSSFCell cellRealisasi = row.createCell(5);
            cellRealisasi.setCellValue(kegiatan.getRealisasi().doubleValue());
            cellRealisasi.setCellStyle(cellStyleLeft);

            XSSFCell cellSisa = row.createCell(6);
            cellSisa.setCellValue(kegiatan.getSisa().doubleValue());
            cellSisa.setCellStyle(cellStyleLeft);

            XSSFCell cellPic = row.createCell(7);
            cellPic.setCellValue(kegiatan.getProgram().getNoUsulan());
            cellPic.setCellStyle(cellStyle);

            XSSFCell cellNominal = row.createCell(8);
            cellNominal.setCellValue(kegiatan.getDate());
            cellNominal.setCellStyle(cellDateStyle);

            counter.getAndIncrement();
            number.getAndIncrement();
        });
    }

    public void generateFooter(XSSFWorkbook workbook, XSSFSheet sheet, int size, BigDecimal sisa) {
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
        numberFormat.setDataFormat(creationHelper.createDataFormat().getFormat("_-[$Rp-id-ID]* #,##0_-;-[$Rp-id-ID]* #,##0_-;_-[$Rp-id-ID]* \"-\"_-;_-@_-"));

        CellRangeAddress cellAddresses = new CellRangeAddress(size + 6, size + 6, 0, 4);
        CellRangeAddress cellAddresses1 = new CellRangeAddress(size + 6, size + 6, 5, 8);
        sheet.addMergedRegion(cellAddresses);
        sheet.addMergedRegion(cellAddresses1);

        XSSFRow row = sheet.createRow(size + 6);
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
        cellnumber3.setCellValue("");
        cellnumber3.setCellStyle(cellStyle);
        XSSFCell cellnumber4 = row.createCell(4);
        cellnumber4.setCellValue("");
        cellnumber4.setCellStyle(cellStyle);
        XSSFCell cellnumber5 = row.createCell(5);
        cellnumber5.setCellValue(sisa.doubleValue());
        cellnumber5.setCellStyle(numberFormat);
        XSSFCell cellnumber6 = row.createCell(6);
        cellnumber6.setCellValue("");
        cellnumber6.setCellStyle(cellStyle);
         XSSFCell cellnumber7 = row.createCell(7);
        cellnumber7.setCellValue("");
        cellnumber7.setCellStyle(cellStyle);
        XSSFCell cellnumber8 = row.createCell(8);
        cellnumber8.setCellValue("");
        cellnumber8.setCellStyle(cellStyle);
    }

    public void generateExcel(HttpServletResponse response, String id) throws IOException {
        List<Kegiatan> kegiatans = kegiatanService.getByProgramId(id);
        Kegiatan kegiatan = kegiatans.get(0);
        int size = kegiatans.size();
        BigDecimal sisa = kegiatanService.getSisa(id);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("TEST ");
        sheet.setColumnWidth(8, 5000);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(2, 400);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(0, 1200);
        generateTitle(workbook, sheet, kegiatan);
        generateHeader(workbook, sheet);
        generateDataList(workbook, sheet, kegiatans);
        generateFooter(workbook, sheet, size, sisa);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
