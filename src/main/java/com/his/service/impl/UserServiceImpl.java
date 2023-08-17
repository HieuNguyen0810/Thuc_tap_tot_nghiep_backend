package com.his.service.impl;

import com.his.constant.ErrorMessage;
import com.his.dto.UserDto;
import com.his.entity.Role;
import com.his.entity.User;
import com.his.exceptionhandler.ApiException;
import com.his.repository.RoleRepo;
import com.his.repository.UserRepo;
import com.his.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// send email
// check lại jwt provider
// export excel file

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    @PostConstruct
    private void initAdmin() {
        Role adminRole = roleRepo.getByName("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = Role.builder()
                    .name("ROLE_ADMIN")
                    .status(true)
                    .build();
            roleRepo.save(adminRole);
        }

        Optional<User> adminExists = userRepository.findByUsername("admin");
        if (!adminExists.isPresent()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setFullName("admin");
            admin.setEmail("admin@admin.com");
            admin.setPhone("0916764424");
            admin.setStatus(true);
            admin.setPassword(passwordEncoder.encode("admin"));
            Role role = roleRepo.getByName("ROLE_ADMIN");
            admin.setRole(role);
            userRepository.save(admin);
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> getByPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        return userRepository.findAll(pageable);
    }

    @Override
    public UserDto getById(Long id) throws ApiException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorMessage.USER_NOT_FOUND));
        return new UserDto(user);
    }



    @Override
    public UserDto create(UserDto userDto) {
        User user = convertDtoToEntity(new User(), userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setStatus(true);
        User createdUser = userRepository.save(user);
        return new UserDto(createdUser);
    }

    @Override
    public UserDto update(Long id, UserDto updatedUserDto) throws ApiException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorMessage.USER_NOT_FOUND));
        convertDtoToEntity(user, updatedUserDto);
        if (updatedUserDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updatedUserDto.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return new UserDto(updatedUser);
    }

    @Override
    public void delete(Long id) throws ApiException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorMessage.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    private List<UserDto> convertToUserDtoList(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return userDtos;
    }

    public static User convertDtoToEntity(User user, UserDto userDto) {
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setCreateTime(userDto.getCreateTime());
        user.setUpdateTime(userDto.getUpdateTime());
        user.setCreatedBy(userDto.getCreatedBy());
        user.setUpdatedBy(userDto.getUpdatedBy());
        user.setStatus(userDto.isStatus());
        user.setRole(RoleServiceImpl.convertToRoleEntity(userDto.getRole()));

        return user;
    }
}
