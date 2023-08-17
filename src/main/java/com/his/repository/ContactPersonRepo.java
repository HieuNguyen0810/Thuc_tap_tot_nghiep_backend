package com.his.repository;

import com.his.entity.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactPersonRepo extends JpaRepository<ContactPerson, Long> {

    ContactPerson getByNameAndPhone(String name, String phone);
}
