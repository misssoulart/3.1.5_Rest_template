
package ru.kata.spring.boot_security.demo.init;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;

@Component
public class PostCon {
    UserService userService;
    RoleService roleService;

    @Autowired
    public PostCon(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void firstInit() {

        roleService.saveRole(new Role("ADMIN"));
        roleService.saveRole(new Role("USER"));

        Set<Role> roleAdmin = new HashSet<>();
        roleAdmin.add(roleService.getRoleByName("ADMIN"));
        Set<Role> roleUser = new HashSet<>();
        roleUser.add(roleService.getRoleByName("USER"));

        User admin = new User();
        admin.setUsername("admin");
        admin.setFullName("admin");
        admin.setPassword("admin");
        //admin.setEmail("admin@u");
        //admin.setAge(18);
        admin.setPassword("admin");
        //admin.setRoles(roleAdmin);
        userService.addUser(admin);

        User user = new User();
        user.setUsername("user");
        user.setFullName("user");
        user.setPassword("user");
        //user.setEmail("user@u");
        //user.setAge(1);
        //user.setRoles(roleUser);
        userService.addUser(user);
    }
}

