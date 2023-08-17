package com.his.api;

import com.his.constant.ErrorMessage;
import com.his.dto.DateRangeDto;
import com.his.dto.response.ApiResponse;
import com.his.entity.Checkup;
import com.his.service.CheckupService;
import com.his.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final CheckupService checkupService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REPORTER')")
    @PostMapping("/get-checkups-report")
    public ApiResponse<List<Checkup>> getCheckupsReport(@RequestBody DateRangeDto dateRangeDto) {
        List<Checkup> checkups = reportService.filterCheckups(dateRangeDto.getFrom(), dateRangeDto.getTo());

        return new ApiResponse<>(ErrorMessage.SUCCESS, checkups);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REPORTER')")
    @PostMapping("/export-to-excel-file")
    public void exportToExcel(HttpServletResponse response, @RequestBody DateRangeDto dateRangeDto) throws Exception {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=checkups_between_" + dateRangeDto.getFrom() + "_and_" + dateRangeDto.getTo() +  ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Checkup> checkups = checkupService.getByCreateTimeRange(dateRangeDto);

        reportService.createExcelFile(response, checkups, dateRangeDto);
    }
}
