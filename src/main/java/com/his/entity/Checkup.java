package com.his.entity;

import com.his.utils.CheckupStatusEnum;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Checkup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
//    private int orderNumber;
    private LocalDateTime registerDate;
    private String registerTime;
    private CheckupStatusEnum status;
    private String customerName;
    private String customerPhone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createdBy;
    private String updatedBy;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        createdBy = SecurityContextHolder.getContext().getAuthentication().getName(); // Lấy thông tin người tạo từ một nguồn khác (ví dụ: Spring Security)
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
        updatedBy = SecurityContextHolder.getContext().getAuthentication().getName(); // Lấy thông tin người cập nhật từ một nguồn khác (ví dụ: Spring Security)
    }
}
