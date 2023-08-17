package com.his.service;

import com.his.dto.DateRangeDto;
import com.his.entity.Checkup;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {
    List<Checkup> filterCheckups(LocalDateTime from, LocalDateTime to);
    void createExcelFile(HttpServletResponse response, List<Checkup> checkups, DateRangeDto dateRangeDto) throws Exception;
}
