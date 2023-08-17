package com.his.service;

import com.his.entity.ContactPerson;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactPersonService {
    List<ContactPerson> getAll();
    ContactPerson getById(Long id);
    ContactPerson create(ContactPerson contactPerson);
    ContactPerson update(Long id, ContactPerson updatedContactPerson);
    void delete(Long id);
    Page<ContactPerson> getByPage(int pageIndex, int pageSize);
}
