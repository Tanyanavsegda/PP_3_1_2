package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Roles;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RolesService;
import ru.kata.spring.boot_security.demo.services.UsersService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
@RequestMapping("/admin")
public class AdminsController {

    private final RolesService rolesService;
    private final UsersService usersService;

    @Autowired
    public AdminsController(RolesService rolesService, UsersService usersService) {
        this.rolesService = rolesService;
        this.usersService = usersService;
    }

    @GetMapping()
    public String adminPage(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("allUsers", usersService.findAll());
        model.addAttribute("oneUser", new User());
        model.addAttribute("roles", rolesService.getAllRoles());
        model.addAttribute("user", user);
        return "/index";
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("upUser", usersService.findOne(id));
        model.addAttribute("allRoles", rolesService.getAllRoles());
        return "redirect:/admin";
    }

    @PostMapping("/new")
    public String create(@RequestParam(value = "name") String name,
                         @RequestParam(value = "lastName") String lastName,
                         @RequestParam(value = "age") int age,
                         @RequestParam(value = "login") String login,
                         @RequestParam(value = "password") String password,
                         @RequestParam("roles") String[] role) {
        Set<Roles> roles = new HashSet<>();
        Arrays.stream(role).forEach(r -> roles.add(rolesService.getRole(r)));
        User user = new User(name, lastName, age, login, password, roles);
        usersService.save(user);
        System.out.println("post");
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        usersService.delete(id);
        System.out.println("del");
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String updateUser(@PathVariable("id") int id, @RequestParam(value = "name") String name,
                             @RequestParam(value = "lastName") String lastName,
                             @RequestParam(value = "age") int age,
                             @RequestParam(value = "login") String login,
                             @RequestParam(value = "password") String password,
                             @RequestParam("roles") String[] role) {
        Set<Roles> roles = new HashSet<>();
        Arrays.stream(role).forEach(r -> roles.add(rolesService.getRole(r)));
        User user = usersService.findOne(id);
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        user.setLogin(login);
        user.setRoles(roles);
        if (!password.equals("")) {
            user.setPassword(password);
        }
        usersService.update(id, user);
        return "redirect:/admin";
    }

}
