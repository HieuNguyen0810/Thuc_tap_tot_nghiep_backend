package com.his.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.his.entity.Customer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerDto extends UserDto {
    private String code;
    private LocalDateTime dob;
    private String address;
    private Long targetId;

    @JsonManagedReference
    private ContactPersonDto contactPerson;

    public CustomerDto(Customer entity) {
        super();
        this.code = entity.getCode();
        this.dob = entity.getDob();
        this.address = entity.getAddress();
        this.contactPerson = new ContactPersonDto(entity.getContactPerson());
    }
}
