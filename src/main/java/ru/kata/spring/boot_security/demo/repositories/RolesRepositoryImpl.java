package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Repository
public class RolesRepositoryImpl implements RolesRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getRoleByName(String name) {
        Role role = null;
        try {
            role = entityManager
                    .createQuery("SELECT r FROM Role r WHERE r.name=:name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("not found role");
        }
        return role;
    }

    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void updateRole(Role role) {
        entityManager.merge(role);
    }

    @Override
    public void removeRole(int id) {
        entityManager.remove(getRoleById(id));
    }

    @Override
    public Role getRoleById(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public List<Role> allRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    public HashSet getSetOfRoles(String[] rolesNames) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : rolesNames) {
            roleSet.add(getRoleByName(role));
        }
        return (HashSet) roleSet;
    }

    public Set<Role> setRoleByName(String name, String[] rolesName) {
        Set<Role> roleSet = new HashSet<>();
        if (rolesName != null) {
            for (String roleName : rolesName) {
                roleSet.add(getRoleByName(roleName));
            }
        }
        return roleSet;
    }
}
