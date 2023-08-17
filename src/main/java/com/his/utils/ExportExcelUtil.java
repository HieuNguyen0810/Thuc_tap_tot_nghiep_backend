package com.his.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;

public class ExportExcelUtil {
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void createHeaderLine(List<String> headerTitles, XSSFWorkbook workbook, XSSFSheet sheet, int rowIndex) {
        Row row = sheet.createRow(rowIndex);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);

        for (int i = 0; i < headerTitles.size(); ++i) {
            createCell(sheet, row, i, headerTitles.get(i), style);
        }
    }

    public static void createCell(XSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }
        else if (value instanceof LocalDateTime) {
            cell.setCellValue(dateTimeFormatter.format((TemporalAccessor) value));
        }
        else if (value instanceof Enum) {
            cell.setCellValue(value.toString());
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
}
