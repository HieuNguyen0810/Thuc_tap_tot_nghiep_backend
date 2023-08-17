package com.his.api;

import com.his.constant.ErrorMessage;
import com.his.dto.response.ApiResponse;
import com.his.entity.ContactPerson;
import com.his.service.ContactPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contact-persons")
@RequiredArgsConstructor
public class ContactPersonController {
    private final ContactPersonService contactPersonService;

    @GetMapping
    public ApiResponse<List<ContactPerson>> getAll() {
        List<ContactPerson> contactPeople = contactPersonService.getAll();
        return new ApiResponse<>(ErrorMessage.SUCCESS, contactPeople);
    }

    @GetMapping("/get-by-page")
    public ApiResponse<Page<ContactPerson>> getByPage(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<ContactPerson> contactPeople = contactPersonService.getByPage(pageIndex, pageSize);
        return new ApiResponse<>(ErrorMessage.SUCCESS, contactPeople);
    }

    @GetMapping("/{id}")
    public ApiResponse<ContactPerson> getById(@PathVariable Long id) {
        ContactPerson contactPerson = contactPersonService.getById(id);
        return new ApiResponse<>(ErrorMessage.SUCCESS, contactPerson);
    }

    @PostMapping
    public ApiResponse<ContactPerson> create(@RequestBody ContactPerson contactPerson) {
        ContactPerson createdContactPerson =  contactPersonService.create(contactPerson);
        return new ApiResponse<>(ErrorMessage.SUCCESS, createdContactPerson);
    }

    @PutMapping("/{id}")
    public ApiResponse<ContactPerson> update(@PathVariable Long id, @RequestBody ContactPerson updatedContactPerson) {
        ContactPerson contactPerson =  contactPersonService.update(id, updatedContactPerson);
        return new ApiResponse<>(ErrorMessage.SUCCESS, contactPerson);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        contactPersonService.delete(id);
        return new ApiResponse<>(ErrorMessage.SUCCESS);
    }
}

