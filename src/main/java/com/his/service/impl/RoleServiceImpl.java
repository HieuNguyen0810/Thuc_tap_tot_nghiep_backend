package com.his.service.impl;

import com.his.dto.RoleDto;
import com.his.entity.Role;
import com.his.repository.RoleRepo;
import com.his.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id, Role updatedRole) {
        Role role = getRoleById(id);
        role.setName(updatedRole.getName());
        role.setStatus(updatedRole.isStatus());
        // Update other properties as needed

        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = getRoleById(id);
        roleRepository.delete(role);
    }


    public static Role convertToRoleEntity(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        role.setStatus(roleDto.isStatus());
        role.setCreateTime(roleDto.getCreateTime());
        role.setUpdateTime(roleDto.getUpdateTime());
        role.setCreatedBy(roleDto.getCreatedBy());
        role.setUpdatedBy(roleDto.getUpdatedBy());
        return role;
    }
}

