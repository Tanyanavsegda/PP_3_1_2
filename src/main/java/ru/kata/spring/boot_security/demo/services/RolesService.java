package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Roles;

import java.util.List;

public interface RolesService {
    List<Roles> getAllRoles();

    Roles getRole(String userRole);

    Roles getRoleById(int id);

    void addRole(Roles role);

}
