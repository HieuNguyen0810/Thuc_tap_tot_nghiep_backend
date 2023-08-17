package com.his.api;

import com.his.constant.ErrorMessage;
import com.his.dto.auth.ChangePasswordDto;
import com.his.dto.response.ApiResponse;
import com.his.entity.Customer;
import com.his.exceptionhandler.ApiException;
import com.his.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ApiResponse<?> getAll() {
        return new ApiResponse<>(ErrorMessage.SUCCESS, customerService.getAll());
    }

    @GetMapping("/get-by-page")
    public ApiResponse<?> getByPage(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return new ApiResponse<>(ErrorMessage.SUCCESS, customerService.getByPage(pageIndex, pageSize));
    }

    @GetMapping("/get-by-email/{email}")
    public ApiResponse<Customer> getByEmail(@PathVariable String email) throws ApiException {
        Customer customer = customerService.getByEmail(email);
        return new ApiResponse<>(ErrorMessage.SUCCESS, customer);
    }

    @GetMapping("/{id}")
    public ApiResponse<Customer> getById(@PathVariable Long id) throws ApiException {
        Customer customer = customerService.getById(id);
        return new ApiResponse<>(ErrorMessage.SUCCESS, customer);
    }

    @PostMapping()
    public ApiResponse<Customer> create(@RequestBody Customer customer) {
        try {
            Customer createdCustomer = customerService.create(customer);
            return new ApiResponse<>(ErrorMessage.SUCCESS, createdCustomer);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorMessage(), null);
        }
    }

    @PostMapping("/change-password")
    public ApiResponse<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) throws ApiException {
        try {
            customerService.changePassword(changePasswordDto);
            return new ApiResponse<>(ErrorMessage.SUCCESS);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorMessage(), null);
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
        try {
            Customer updatedCustomer = customerService.update(id, customer);
            return new ApiResponse<>(ErrorMessage.SUCCESS, updatedCustomer);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        try {
            customerService.delete(id);
            return new ApiResponse<>(ErrorMessage.SUCCESS, "Customer deleted successfully");
        } catch (ApiException e) {
            return new ApiResponse<>(e.getErrorMessage(), null);
        }
    }
}