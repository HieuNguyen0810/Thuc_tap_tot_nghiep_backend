package com.his.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {
    private User user;
    private Customer customer;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public CustomUserDetails(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return this.user != null ?  User.builder()
                .id(this.user.getId())
                .username(this.user.getUsername())
                .fullName(this.user.getFullName())
                .build()
                : null;
    }

    public Customer getCustomer() {
        return this.customer != null ? Customer.builder()
                .id(this.customer.getId())
                .fullName(this.customer.getFullName())
                .email(this.customer.getEmail())
                .build()
                : null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority( (this.user != null) ?
                        this.user.getRole().getName() :
                        this.customer.getRole().getName()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user != null ?
                 this.user.getPassword() :
                this.customer.getPassword();
    }

    @Override
    public String getUsername() {
//        return this.user.getEmail();

        return this.user != null ?
                this.user.getUsername() :
                this.customer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

