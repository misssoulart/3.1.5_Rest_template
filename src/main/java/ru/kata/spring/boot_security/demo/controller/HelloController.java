package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class HelloController {
    private final UserService userService;

    @Autowired()
    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String printWelcome(ModelMap model, Principal princ) {
        model.addAttribute("MainUser",userService.loadUserByUsername(princ.getName()));
        return "user/user-page";
    }
}