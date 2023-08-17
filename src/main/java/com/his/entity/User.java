package com.his.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    @NotNull(message = "Full name must be required!")
    private String fullName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    @NotNull(message = "Password must be required!")
    private String password;

    @Email(message = "The email is not correct format!")
    @NotNull(message = "Email must be required!")
    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "\\d{10}", message = "Invalid phone number")
    private String phone;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createdBy;
    private String updatedBy;
    private boolean status;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        String authenticationName = SecurityContextHolder.getContext().getAuthentication() != null ? SecurityContextHolder.getContext().getAuthentication().getName() : null;
        createdBy =  authenticationName != null ? authenticationName : "";
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
        String authenticationName = SecurityContextHolder.getContext().getAuthentication() != null ? SecurityContextHolder.getContext().getAuthentication().getName() : null;
        updatedBy =  authenticationName != null ? authenticationName : "";
    }
}

