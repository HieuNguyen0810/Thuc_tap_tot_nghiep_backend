package com.his.service;

import com.his.dto.DateRangeDto;
import com.his.entity.Checkup;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CheckupService {
    List<Checkup> getAllCheckups();
    Page<Checkup> getByPage(int pageIndex, int pageSize);
    Checkup getCheckupById(Long id);
    Checkup createCheckup(Checkup checkup);
    Checkup updateCheckup(Long id, Checkup updatedCheckup);
    void deleteCheckup(Long id);
    List<Checkup> getByCreateTimeRange(DateRangeDto dateRangeDto);

    Page<Checkup> getByCustomerId(Integer customerId, int pageIndex, int pageSize);

    int countByCustomerId(Long id);
}
