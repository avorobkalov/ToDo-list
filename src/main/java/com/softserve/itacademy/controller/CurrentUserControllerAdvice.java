package com.softserve.itacademy.controller;

import com.softserve.itacademy.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserControllerAdvice {

    private UserDetailsService userDetailsService;

    @Autowired
    public CurrentUserControllerAdvice(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @ModelAttribute("currentUser")
    public SecurityUser getCurrentUser(Authentication authentication) {
        return (authentication == null) ? null : (SecurityUser)userDetailsService.loadUserByUsername(authentication.getName());
    }
}
