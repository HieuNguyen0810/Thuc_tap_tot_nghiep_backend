package com.his.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// doi tuong
public class Target {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;

    @Column(nullable = false)
    private String name;
    private LocalDateTime createTime;
    private String createdBy;
    private boolean status;

    @OneToMany(mappedBy = "target")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Customer> customers;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        createdBy = SecurityContextHolder.getContext().getAuthentication().getName(); // Lấy thông tin người tạo từ một nguồn khác (ví dụ: Spring Security)
    }

//    @PreUpdate
//    protected void onUpdate() {
//        updateTime = LocalDateTime.now();
//        updatedBy = SecurityContextHolder.getContext().getAuthentication().getName(); // Lấy thông tin người cập nhật từ một nguồn khác (ví dụ: Spring Security)
//    }
}


