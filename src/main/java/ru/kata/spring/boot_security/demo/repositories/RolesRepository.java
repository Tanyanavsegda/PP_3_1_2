package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Roles findByRoleName(String roleName);
}
