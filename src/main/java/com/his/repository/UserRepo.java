package com.his.repository;

import com.his.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

//    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String username);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);
}

