package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Roles;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RolesServiceImpl implements RolesService {

    private final RolesRepository rolesRepository;


    public RolesServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }
    @Override
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public Roles getRole(String userRole) {
        return rolesRepository.findByRoleName(userRole);
    }

    @Override
    public Roles getRoleById(int id) {
        Optional<Roles> foundRole = rolesRepository.findById(id);
        return foundRole.orElse(null);
    }

    @Override
    public void addRole(Roles role) {
        rolesRepository.save(role);
    }
}
