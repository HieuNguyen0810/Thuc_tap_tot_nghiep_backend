package com.his.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.his.entity.Role;
import com.his.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;

    private String name;

    private boolean status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createdBy;

    private String updatedBy;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<UserDto> users;

    public RoleDto(Role entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.status = entity.isStatus();
        this.createTime = entity.getCreateTime();
        this.updateTime = entity.getUpdateTime();
        this.createdBy = entity.getCreatedBy();
        this.updatedBy = entity.getUpdatedBy();
//        this.users = convertToUserDtoList(entity.getUsers());
    }

    private List<UserDto> convertToUserDtoList(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return userDtos;
    }
}

