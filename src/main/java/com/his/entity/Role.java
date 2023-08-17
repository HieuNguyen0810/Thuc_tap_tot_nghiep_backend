package com.his.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createdBy;
    private String updatedBy;

//    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Set<User> users;

    @OneToMany(mappedBy = "role")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<User> users;

    @OneToMany(mappedBy = "role")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Customer> customers;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
//        createdBy = SecurityContextHolder.getContext().getAuthentication().getName(); // Lấy thông tin người tạo từ một nguồn khác (ví dụ: Spring Security)
        String authenticationName = SecurityContextHolder.getContext().getAuthentication() != null ? SecurityContextHolder.getContext().getAuthentication().getName() : null;
        createdBy =  authenticationName != null ? authenticationName : "";
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
//        updatedBy = SecurityContextHolder.getContext().getAuthentication().getName(); // Lấy thông tin người cập nhật từ một nguồn khác (ví dụ: Spring Security)
        String authenticationName = SecurityContextHolder.getContext().getAuthentication() != null ? SecurityContextHolder.getContext().getAuthentication().getName() : null;
        updatedBy =  authenticationName != null ? authenticationName : "";
    }
}

