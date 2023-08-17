package com.his.api;

import com.his.constant.ErrorMessage;
import com.his.dto.response.ApiResponse;
import com.his.entity.Checkup;
import com.his.service.CheckupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/checkups")
@RequiredArgsConstructor
public class CheckupController {
    private final CheckupService checkupService;

    @GetMapping
    public ApiResponse<List<Checkup>> getAll() {
        List<Checkup> checkups = checkupService.getAllCheckups();
        return new ApiResponse<>(ErrorMessage.SUCCESS, checkups);
    }

    @GetMapping("/get-by-page")
    public ApiResponse<Page<Checkup>> getByPage(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<Checkup> checkups = checkupService.getByPage(pageIndex, pageSize);
        return new ApiResponse<>(ErrorMessage.SUCCESS, checkups);
    }

    @GetMapping("/{id}")
    public ApiResponse<Checkup> getById(@PathVariable Long id) {
        Checkup checkup = checkupService.getCheckupById(id);
        return new ApiResponse<>(ErrorMessage.SUCCESS, checkup);
    }

    @GetMapping("/count/{id}")
    public ApiResponse<Integer> countById(@PathVariable Long id) {
        return new ApiResponse<>(ErrorMessage.SUCCESS, checkupService.countByCustomerId(id));
    }

    @GetMapping("/get-by-customer/{customerId}")
    public ApiResponse<Page<Checkup>> getByCustomerId(@PathVariable Integer customerId,
                                                      @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<Checkup> checkups = checkupService.getByCustomerId(customerId, pageIndex, pageSize);
        return new ApiResponse<>(ErrorMessage.SUCCESS, checkups);
    }

    @PostMapping("")
    public ApiResponse<Checkup> create(@RequestBody Checkup checkup) {
        Checkup createdCheckup = checkupService.createCheckup(checkup);
        return new ApiResponse<>(ErrorMessage.SUCCESS, createdCheckup);
    }

    @PutMapping("/{id}")
    public ApiResponse<Checkup> update(@PathVariable Long id, @RequestBody Checkup updatedCheckup) {
        Checkup checkup = checkupService.updateCheckup(id, updatedCheckup);
        return new ApiResponse<>(ErrorMessage.SUCCESS, checkup);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        checkupService.deleteCheckup(id);
        return new ApiResponse<>(ErrorMessage.SUCCESS);
    }
}

