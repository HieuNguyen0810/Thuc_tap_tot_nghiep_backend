package com.his.repository;

import com.his.entity.Checkup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CheckupRepo extends JpaRepository<Checkup, Long> {
//    List<Checkup> findByRegisterDateOrderByOrderNumberAsc(LocalDateTime registerDate);

//    Optional<Checkup> findFirstByRegisterDateOrderByOrderNumberDesc(LocalDateTime registerDate);

//    @Query(value = "select * from  checkup where create_time between ?1 and ?2", nativeQuery = true)
    @Query("select c from Checkup c where c.registerDate between ?1 and ?2")
    List<Checkup> findAllByCreateTimeRange(LocalDateTime from, LocalDateTime to);

    @Query("select c from Checkup c where c.registerDate between ?1 and ?2")
    List<Checkup> findPageByCreateTimeRange(LocalDateTime from, LocalDateTime to);

    Page<Checkup> findByCustomerId(Long customerId, Pageable pageable);

    int countByCustomerId(Long id);
}
