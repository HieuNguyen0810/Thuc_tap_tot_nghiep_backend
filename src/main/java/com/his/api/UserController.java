package com.his.api;

import com.his.constant.ErrorMessage;
import com.his.dto.UserDto;
import com.his.dto.response.ApiResponse;
import com.his.entity.User;
import com.his.exceptionhandler.ApiException;
import com.his.repository.UserRepo;
import com.his.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepo userRepo;
    private final UserService userService;

    @GetMapping
    public ApiResponse<List<User>> getAll() {
        List<User> users = userRepo.findAll();
        return new ApiResponse<>(ErrorMessage.SUCCESS, users);
    }

    @GetMapping("/get-by-page")
    public ApiResponse<Page<User>> getByPage(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<User> users = userService.getByPage(pageIndex, pageSize);
        return new ApiResponse<>(ErrorMessage.SUCCESS, users);
    }

    @PreAuthorize("@UserSecurity.hasUserId(authentication, #id)")
    @GetMapping("/{id}")
    public ApiResponse<UserDto> getById(@PathVariable Long id) throws ApiException {
        UserDto user = userService.getById(id);
        return new ApiResponse<>(ErrorMessage.SUCCESS, user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ApiResponse<UserDto> create(@RequestBody UserDto user) {
        UserDto createdUser = userService.create(user);
        return new ApiResponse<>(ErrorMessage.SUCCESS, createdUser);
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDto> update(@PathVariable Long id, @RequestBody UserDto updatedUser) throws ApiException {
        UserDto user = userService.update(id, updatedUser);
        return new ApiResponse<>(ErrorMessage.SUCCESS, user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteUser(@PathVariable Long id) throws ApiException {
        userService.delete(id);
        return new ApiResponse<>(ErrorMessage.SUCCESS);
    }
}
