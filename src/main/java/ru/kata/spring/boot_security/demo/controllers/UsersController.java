package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UsersService;


@Controller
@RequestMapping("/")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }


    @GetMapping("/user")
    public String user(@AuthenticationPrincipal UserDetails user, Model model) {
        User foundUser = usersService.findByLogin(user.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("foundUser", foundUser);
        return "/user";
    }


}

