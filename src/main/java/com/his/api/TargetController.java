package com.his.api;

import com.his.constant.ErrorMessage;
import com.his.dto.response.ApiResponse;
import com.his.entity.Target;
import com.his.exceptionhandler.ApiException;
import com.his.service.TargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/targets")
@RequiredArgsConstructor
public class TargetController {
    private final TargetService targetService;

    @PostMapping
    public ApiResponse<Target> createTarget(@RequestBody Target target) {
        Target createdTarget = targetService.create(target);
        return new ApiResponse<>(ErrorMessage.SUCCESS, createdTarget);
    }

    @GetMapping
    public ApiResponse<?> getAll() {
        return new ApiResponse<>(ErrorMessage.SUCCESS, targetService.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Target> getById(@PathVariable Long id) {
        try {
            Target target = targetService.getById(id);
            return new ApiResponse<>(ErrorMessage.SUCCESS, target);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorMessage(), null);
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Target> update(@PathVariable Long id, @RequestBody Target target) {
        try {
            Target updatedTarget = targetService.update(id, target);
            return new ApiResponse<>(ErrorMessage.SUCCESS, updatedTarget);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteTarget(@PathVariable Long id) {
        try {
            targetService.delete(id);
            return new ApiResponse<>(ErrorMessage.SUCCESS);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorMessage(), null);
        }
    }
}

