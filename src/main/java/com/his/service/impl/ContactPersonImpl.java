package com.his.service.impl;

import com.his.entity.ContactPerson;
import com.his.entity.Customer;
import com.his.entity.User;
import com.his.repository.ContactPersonRepo;
import com.his.repository.CustomerRepo;
import com.his.service.ContactPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactPersonImpl implements ContactPersonService {
    private final ContactPersonRepo contactPersonRepository;
    private final CustomerRepo customerRepo;


    @Override
    public List<ContactPerson> getAll() {
        return contactPersonRepository.findAll();
    }

    @Override
    public ContactPerson getById(Long id) {
        return contactPersonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact person not found"));
    }

    @Override
    public ContactPerson create(ContactPerson contactPerson) {
        return contactPersonRepository.save(contactPerson);
    }

    @Override
    public ContactPerson update(Long id, ContactPerson updatedContactPerson) {
        ContactPerson contactPerson = getById(id);
        contactPerson.setName(updatedContactPerson.getName());
        contactPerson.setPhone(updatedContactPerson.getPhone());
        contactPerson.setEmail(updatedContactPerson.getEmail());

        return contactPersonRepository.save(contactPerson);
    }

    @Override
    public void delete(Long id) {
        ContactPerson contactPerson = getById(id);
        contactPersonRepository.delete(contactPerson);
    }

    @Override
    public Page<ContactPerson> getByPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return contactPersonRepository.findAll(pageable);
    }
}
