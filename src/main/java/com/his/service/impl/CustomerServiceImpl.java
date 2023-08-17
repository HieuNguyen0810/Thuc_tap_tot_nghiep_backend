package com.his.service.impl;

import com.his.constant.ErrorMessage;
import com.his.dto.auth.ChangePasswordDto;
import com.his.entity.ContactPerson;
import com.his.entity.Customer;
import com.his.entity.Target;
import com.his.exceptionhandler.ApiException;
import com.his.repository.ContactPersonRepo;
import com.his.repository.CustomerRepo;
import com.his.repository.TargetRepo;
import com.his.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepository;
    private final TargetRepo targetRepo;
    private final ContactPersonRepo contactPersonRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Customer getById(Long id) throws ApiException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorMessage.USER_NOT_FOUND));
    }

    @Override
    public Customer create(Customer customer) throws ApiException {
        ContactPerson contactPerson = customer.getContactPerson();
        ContactPerson existedContactPerson = null;

        if (contactPerson != null) {
            existedContactPerson = contactPersonRepo.getByNameAndPhone(contactPerson.getName(), contactPerson.getPhone());

            if (existedContactPerson == null) {
                existedContactPerson = contactPersonRepo.save(contactPerson);

                List<Customer> customers = new ArrayList<>();
                customers.add(customer);
                existedContactPerson.setCustomers(customers);
            } else {
                List<Customer> customers = contactPerson.getCustomers();
                customers.add(customer);
                existedContactPerson.setCustomers(customers);
            }

            existedContactPerson = contactPersonRepo.save(existedContactPerson);
        }
        customer.setContactPerson(existedContactPerson);

        if (customer.getTarget() != null) {
            Target target = targetRepo.getByCode(customer.getTarget().getCode());
            customer.setTarget(target);
        }

        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Long id, Customer updatedCustomer) throws ApiException {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorMessage.USER_NOT_FOUND));

        updatedCustomer.setId(id);
        updatedCustomer.setPassword(existingCustomer.getPassword());

        ContactPerson contactPerson = updatedCustomer.getContactPerson();
        ContactPerson existedContactPerson = null;

        if (contactPerson != null) {
            existedContactPerson = contactPersonRepo.getByNameAndPhone(contactPerson.getName(), contactPerson.getPhone());
            if (existedContactPerson == null) {
                existedContactPerson = contactPersonRepo.save(contactPerson);
            }
        }

        updatedCustomer.setContactPerson(existedContactPerson);

        return customerRepository.save(updatedCustomer);
    }

    @Override
    public void delete(Long id) throws ApiException {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorMessage.USER_NOT_FOUND));
        customerRepository.delete(existingCustomer);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Page<Customer> getByPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer getByEmail(String email) throws ApiException {
        return customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ApiException(ErrorMessage.USER_NOT_FOUND));
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto) throws ApiException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                System.out.println("Username: " + username);

                Customer customer = customerRepository.findByEmail(username)
                        .orElseThrow(() -> new ApiException(ErrorMessage.USER_NOT_FOUND));

                if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), customer.getPassword())) {
                    throw new ApiException(ErrorMessage.PASSWORD_IS_INCORRECT);
                }

                String hashedNewPassword = passwordEncoder.encode(changePasswordDto.getNewPassword());
                customer.setPassword(hashedNewPassword);
                customerRepository.save(customer);
            }
        }
    }
}
