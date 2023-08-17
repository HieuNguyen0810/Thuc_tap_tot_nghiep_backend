package com.his.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.his.entity.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String username;

    @NotNull(message = "Full name must be required!")
    private String fullName;

    @Email(message = "The email is not in the correct format!")
    @NotNull(message = "Email must be required!")
    private String email;

    private String password;


    @Pattern(regexp = "\\d{10}", message = "Invalid phone number")
    private String phone;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createdBy;
    private String updatedBy;
    private boolean status;
    private RoleDto role;


    public UserDto(User entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.fullName = entity.getFullName();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
        this.createTime = entity.getCreateTime();
        this.updateTime = entity.getUpdateTime();
        this.createdBy = entity.getCreatedBy();
        this.updatedBy = entity.getUpdatedBy();
        this.status = entity.isStatus();
        this.role = new RoleDto(entity.getRole());
    }
}

