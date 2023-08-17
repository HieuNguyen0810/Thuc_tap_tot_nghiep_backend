package com.his.service;

import com.his.dto.auth.ChangePasswordDto;
import com.his.entity.Customer;
import com.his.exceptionhandler.ApiException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    Customer getById(Long id) throws ApiException;
    Customer create(Customer customer) throws ApiException;
    Customer update(Long id, Customer updatedCustomer) throws ApiException;
    void delete(Long id) throws ApiException;

    List<Customer> getAll();

    Page<Customer> getByPage(int pageIndex, int pageSize);

    Customer getByEmail(String email) throws ApiException;

    void changePassword(ChangePasswordDto changePasswordDto) throws ApiException;
}
