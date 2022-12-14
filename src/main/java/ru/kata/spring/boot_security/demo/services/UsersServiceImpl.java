package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Roles;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UsersServiceImpl implements UsersService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public User findOne(int id) {
        Optional<User> foundUser = usersRepository.findById(id);
        return foundUser.orElse(null);
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    @Transactional
    public void update(int id, User updateUser) {
        updateUser.setUserrId(id);
        usersRepository.save(updateUser);
    }

    @Override
    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

    @Override
    public User findByLogin(String login) {
        return usersRepository.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User findUser = usersRepository.findByLogin(username);
        if (findUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(findUser.getUsername(), findUser.getPassword(),
                mapRoles(findUser.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRoles(Collection<Roles> roles) {
        return roles.stream().map(e -> new SimpleGrantedAuthority(e.getRoleName())).collect(Collectors.toList());
    }
}
