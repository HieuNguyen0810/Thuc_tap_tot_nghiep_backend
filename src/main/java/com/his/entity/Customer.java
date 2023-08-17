package com.his.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @NotNull(message = "Email must be required!")
    @Pattern(regexp = "\\d{10}", message = "Invalid phone number")
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createdBy;
    private String updatedBy;
    private boolean status;

    private String code;
    private LocalDateTime dob;

    @OneToMany(mappedBy = "customer")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Checkup> checkups;

    @ManyToOne
    @JoinColumn(name = "target_id")
    private Target target;

    @ManyToOne
    @JoinColumn(name = "contact_person_id")
    private ContactPerson contactPerson;
    private String address;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;




    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
        updatedBy = SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
