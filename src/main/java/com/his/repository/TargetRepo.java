package com.his.repository;

import com.his.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepo extends JpaRepository<Target, Long> {
    Target getByCode(String code);
}
