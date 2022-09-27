package ru.kata.spring.boot_security.demo.repositories;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface RolesRepository {
    Role getRoleByName(String name);

    Role getRoleById(int id);

    List<Role> allRoles();

    HashSet<Role> getSetOfRoles(String[] roleSet);

    Set<Role> setRoleByName(String name, String[] rolesName);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(int id);
}
