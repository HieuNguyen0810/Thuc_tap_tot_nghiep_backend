package com.his.service.impl;

import com.his.dto.DateRangeDto;
import com.his.entity.Checkup;
import com.his.entity.Customer;
import com.his.repository.CheckupRepo;
import com.his.repository.CustomerRepo;
import com.his.service.CheckupService;
import com.his.utils.CheckupStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckupServiceImpl implements CheckupService {
    private final CheckupRepo checkupRepository;
    private final CustomerRepo customerRepo;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<Checkup> getAllCheckups() {
        return checkupRepository.findAll();
    }

    @Override
    public Page<Checkup> getByPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        return checkupRepository.findAll(pageable);
    }

    @Override
    public Checkup getCheckupById(Long id) {
        return checkupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Checkup not found"));
    }

    @Override
    public Checkup createCheckup(Checkup createCheckup) {
        Customer customer = null;
        Checkup checkup = new Checkup();
        checkup.setStatus(CheckupStatusEnum.PENDING);
        checkup.setRegisterDate(createCheckup.getRegisterDate());
        checkup.setRegisterTime(createCheckup.getRegisterTime());

        if (createCheckup.getCustomer() != null) {
            Optional<Customer> optionalCustomer = customerRepo.findByEmail(createCheckup.getCustomer().getEmail());
            if (optionalCustomer.isPresent()) {
                customer = optionalCustomer.get();

                checkup.setCustomer(customer);
                customer.getCheckups().add(checkup);
                customerRepo.save(customer);
            }
        }
        if ("".equals(createCheckup.getCustomerName()) && customer != null) {
            checkup.setCustomerName(customer.getFullName());
        } else {
            checkup.setCustomerName(createCheckup.getCustomerName());
        }
        if ("".equals(createCheckup.getCustomerPhone()) && customer != null) {
            checkup.setCustomerPhone(customer.getPhone());
        } else {
            checkup.setCustomerPhone(createCheckup.getCustomerPhone());
        }

        return checkupRepository.save(checkup);
    }

    @Override
    public Checkup updateCheckup(Long id, Checkup updatedCheckup) {
        Checkup checkup = getCheckupById(id);
        checkup.setCode(updatedCheckup.getCode());
//        checkup.setOrderNumber(updatedCheckup.getOrderNumber());
        checkup.setRegisterDate(updatedCheckup.getRegisterDate());
        checkup.setRegisterTime(updatedCheckup.getRegisterTime());
        checkup.setStatus(updatedCheckup.getStatus());
        if (!"".equals(updatedCheckup.getCustomerName())) {
            checkup.setCustomerName(updatedCheckup.getCustomerName());
        }
        if (!"".equals(updatedCheckup.getCustomerPhone())) {
            checkup.setCustomerPhone(updatedCheckup.getCustomerPhone());
        }

        return checkupRepository.save(checkup);
    }

    @Override
    public void deleteCheckup(Long id) {
        Checkup checkup = getCheckupById(id);
        checkupRepository.delete(checkup);
    }

    @Override
    public List<Checkup> getByCreateTimeRange(DateRangeDto dateRangeDto) {
        return checkupRepository.findAllByCreateTimeRange(dateRangeDto.getFrom(), dateRangeDto.getTo());
    }

    @Override
    public Page<Checkup> getByCustomerId(Integer customerId, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return checkupRepository.findByCustomerId(Long.valueOf(customerId), pageable);
    }

    @Override
    public int countByCustomerId(Long id) {
        return checkupRepository.countByCustomerId(id);
    }
}
