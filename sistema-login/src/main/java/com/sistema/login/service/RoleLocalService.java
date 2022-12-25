package com.sistema.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.login.model.Role;
import com.sistema.login.service.persistence.RolePersistence;

@Service
public class RoleLocalService {

    public Role findByName(String name) {
        return rolePersistence.findByName(name);
    }

    public Role save(Role role) {
        return rolePersistence.save(role);
    }

    @Autowired
    private RolePersistence rolePersistence;

}
