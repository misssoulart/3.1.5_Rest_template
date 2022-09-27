package ru.kata.spring.boot_security.demo.repositories;

import org.apache.catalina.User;

import java.util.List;

public interface UserRepositnory {
    void addUser(User user);

    void removeUser(int id);

    void editUser(User user);

    User getUserById(int id);

    List<User> getAllUsers();

    User getUserByUsername(String username);
}
