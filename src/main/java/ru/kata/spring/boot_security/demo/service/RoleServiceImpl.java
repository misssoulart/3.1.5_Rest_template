package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;

import javax.management.relation.Role;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private RolesRepository rolesRepository;

    @Autowired
    public void setRolesRepository(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Role getRoleByName(String name) {
        return rolesRepository.getRoleByName(name);
    }

    @Override
    public Role getRoleById(int id) {
        return rolesRepository.getRoleById(id);
    }

    @Override
    public List<Role> allRoles() {
        return rolesRepository.allRoles();
    }

    @Override
    public HashSet<Role> getSetOfRoles(String[] roleSet) {
        return rolesRepository.getSetOfRoles(roleSet);
    }

    @Override
    public Set<Role> setRoleByName(String name, String[] rolesName) {
        return rolesRepository.setRoleByName(name, rolesName);
    }

    @Override
    public void saveRole(Role role) {
        rolesRepository.saveRole(role);
    }

    @Override
    public void updateRole(Role role) {
        rolesRepository.updateRole(role);
    }

    @Override
    public void removeRole(int id) {
        rolesRepository.removeRole(id);
    }

    @Override
    public void addRole(Role roleAdmin) {

    }
}
