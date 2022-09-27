package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepositnory;

import java.util.List;

@Service
@Transactional
public abstract class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepositnory userRepository;


    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.addUser((org.apache.catalina.User) user);
    }

    @Override
    public void removeUser(int id) {
        userRepository.removeUser(id);
    }

    @Override
    public void updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.editUser((org.apache.catalina.User) user);
    }

    @Override
    public org.apache.catalina.User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    public List<org.apache.catalina.User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User findByUsername(String username) {
        return (User) userRepository.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.getUserByUsername(username);
    }
}