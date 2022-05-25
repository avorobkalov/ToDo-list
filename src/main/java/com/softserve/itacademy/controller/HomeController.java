package com.softserve.itacademy.controller;

import com.softserve.itacademy.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('admin-access')")
    @GetMapping({"/", "home"})
    public String home(Model model) {
        model.addAttribute("users", userService.getAll());
        return "home";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("main")
    public String main() {
        return "start-page";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied(Principal user) {

        ModelAndView model = new ModelAndView("error", HttpStatus.FORBIDDEN);

        if (user != null) {
            model.addObject("message", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            model.addObject("message",
                    "You do not have permission to access this page!");
        }
        model.addObject("status_code", model.getStatus().value());
        model.setViewName("error");
        return model;

    }
}
