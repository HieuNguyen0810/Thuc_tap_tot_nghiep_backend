package com.his.api;

import com.his.constant.ErrorMessage;
import com.his.dto.response.ApiResponse;
import com.his.entity.Role;
import com.his.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ApiResponse<List<Role>> getAll() {
        List<Role> roles = roleService.getAllRoles();

        return new ApiResponse<>(ErrorMessage.SUCCESS, roles);
    }

    @GetMapping("/{id}")
    public ApiResponse<Role> getById(@PathVariable Long id) {
        Role role =  roleService.getRoleById(id);
        return new ApiResponse<>(ErrorMessage.SUCCESS, role);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ApiResponse<Role> create(@RequestBody Role role) {
        Role createdRole = roleService.createRole(role);
        return new ApiResponse<>(ErrorMessage.SUCCESS, createdRole);
    }

    @PutMapping("/{id}")
    public ApiResponse<Role> update(@PathVariable Long id, @RequestBody Role role) {
        Role updatedRole = roleService.updateRole(id, role);
        return new ApiResponse<>(ErrorMessage.SUCCESS, updatedRole);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ApiResponse<>(ErrorMessage.SUCCESS);
    }
}

