package com.his.service.impl;

import com.his.dto.DateRangeDto;
import com.his.entity.Checkup;
import com.his.repository.CheckupRepo;
import com.his.service.ReportService;
import com.his.utils.CheckupStatusEnum;
import com.his.utils.ExportExcelUtil;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final CheckupRepo checkupRepo;


    // from, to: YYYY-MM-DD
    public List<Checkup> filterCheckups(LocalDateTime from, LocalDateTime to) {
        return checkupRepo.findPageByCreateTimeRange(from, to);
    }

    public void createExcelFile(HttpServletResponse response, List<Checkup> checkups, DateRangeDto dateRangeDto) throws Exception {

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
            long totalCheckupsCount = checkups.size();
            long totalConfirmedCheckupsCount = checkups.stream().filter(checkup -> checkup.getStatus().equals(CheckupStatusEnum.CONFIRMED)).count();
            long totalPendingCheckupsCount = checkups.stream().filter(checkup -> checkup.getStatus().equals(CheckupStatusEnum.PENDING)).count();
            long totalRejectedCheckupsCount = checkups.stream().filter(checkup -> checkup.getStatus().equals(CheckupStatusEnum.REJECTED)).count();

            XSSFSheet sheet = workbook.createSheet("Checkup Report");

            List<String> headerTitles = Arrays.asList("ID", "Tên khách hàng", "Điện thoại", "Ngày đăng ký", "Buổi", "Trạng thái",
                    "Ngày tạo", "Người tạo", "Ngày cập nhật", "Người cập nhật");

            sheet.addMergedRegion(CellRangeAddress.valueOf("A1:J1"));
            Row row = sheet.createRow(0);
            row.setHeight((short) 1000);
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            XSSFFont font = workbook.createFont();
            font.setFontName("Times New Roman");
            font.setFontHeight(16);
            font.setBold(true);
            style.setFont(font);
            ExportExcelUtil.createCell(sheet, row, 0, "Báo cáo số lượng đăng ký khám", style);

            //
            int rowCount = 2;
            style = workbook.createCellStyle();
            font = workbook.createFont();
            font.setFontName("Times New Roman");
            font.setFontHeight(13);
            font.setBold(true);
            style.setFont(font);

            row = sheet.createRow(rowCount++);
            ExportExcelUtil.createCell(sheet, row, 0, "Tổng số đăng ký:", style);
            ExportExcelUtil.createCell(sheet, row, 1, totalCheckupsCount, style);
            ExportExcelUtil.createCell(sheet, row, 8, "Từ ngày:", style);
            ExportExcelUtil.createCell(sheet, row, 9, formatter.format(dateRangeDto.getFrom()), style);


            row = sheet.createRow(rowCount++);
            ExportExcelUtil.createCell(sheet, row, 0, "Đã xác nhận:", style);
            ExportExcelUtil.createCell(sheet, row, 1, totalConfirmedCheckupsCount, style);
            ExportExcelUtil.createCell(sheet, row, 8, "Đến ngày", style);
            ExportExcelUtil.createCell(sheet, row, 9, formatter.format(dateRangeDto.getTo()), style);


            row = sheet.createRow(rowCount++);
            ExportExcelUtil.createCell(sheet, row, 0, "Chưa xác nhận:", style);
            ExportExcelUtil.createCell(sheet, row, 1, totalPendingCheckupsCount, style);


            row = sheet.createRow(rowCount++);
            ExportExcelUtil.createCell(sheet, row, 0, "Đã từ chối: ", style);
            ExportExcelUtil.createCell(sheet, row, 1, totalRejectedCheckupsCount, style);
            //

            ++rowCount;
            ExportExcelUtil.createHeaderLine(headerTitles, workbook, sheet, rowCount++);
            createDataLines(workbook, sheet, checkups, rowCount++);

            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();

            outputStream.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void createDataLines(XSSFWorkbook workbook, XSSFSheet sheet, List<Checkup> checkups, int rowCount) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeight(13);
        style.setFont(font);


        for (Checkup checkup: checkups) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            ExportExcelUtil.createCell(sheet, row, columnCount++, checkup.getId(), style);
            ExportExcelUtil.createCell(sheet, row, columnCount++, checkup.getCustomerName(), style);
            ExportExcelUtil.createCell(sheet, row, columnCount++, checkup.getCustomerPhone(), style);
            ExportExcelUtil.createCell(sheet, row, columnCount++, checkup.getRegisterDate(), style);
            ExportExcelUtil.createCell(sheet, row, columnCount++, checkup.getRegisterTime(), style);
            ExportExcelUtil.createCell(sheet, row, columnCount++, checkup.getStatus(), style);
            ExportExcelUtil.createCell(sheet, row, columnCount++, checkup.getCreateTime(), style);
            ExportExcelUtil.createCell(sheet, row, columnCount++, checkup.getCreatedBy(), style);
            ExportExcelUtil.createCell(sheet, row, columnCount++, checkup.getUpdateTime(), style);
            ExportExcelUtil.createCell(sheet, row, columnCount++, checkup.getUpdatedBy(), style);
        }
    }
}
