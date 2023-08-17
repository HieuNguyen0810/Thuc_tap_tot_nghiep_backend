package com.his.service;

import com.his.dto.UserDto;
import com.his.entity.User;
import com.his.exceptionhandler.ApiException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<User> getAll();

    Page<User> getByPage(int pageIndex, int pageSize);
    UserDto getById(Long id) throws ApiException;
    UserDto create(UserDto user);
    UserDto update(Long id, UserDto updatedUser) throws ApiException;
    void delete(Long id) throws ApiException;

}

