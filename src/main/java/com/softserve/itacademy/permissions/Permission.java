package com.softserve.itacademy.permissions;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Permission {
    ADMIN(Set.of("admin-access")),
    USER(Set.of("user-access"));

    private final Set<SimpleGrantedAuthority> authorities;

    Permission(Set<String> authorities) {
        this.authorities = authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toSet());
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }
}
