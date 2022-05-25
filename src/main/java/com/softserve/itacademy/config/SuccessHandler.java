package com.softserve.itacademy.config;

import com.softserve.itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

    private UserService userService;

    @Autowired
    public SuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (AuthorityUtils.authorityListToSet(authentication.getAuthorities())
                .contains("admin-access")) {
            httpServletResponse.sendRedirect("/");
        }
        else {
            httpServletResponse.sendRedirect("/todos/all/users/" +
                    userService.findByEmail(authentication.getName()).getId());
        }
    }
}
