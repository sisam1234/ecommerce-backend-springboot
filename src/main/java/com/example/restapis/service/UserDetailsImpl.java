package com.example.restapis.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.restapis.entity.User;

public class UserDetailsImpl implements UserDetails  {
private User user; // wrap your User entity

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // we’ll use email to login
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For now, simple ROLE_USER
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    // Optional: expose other User fields like name, id
    public Long getId() { return user.getId(); }
    public String getName() { return user.getName(); }
}
