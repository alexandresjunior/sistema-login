package com.sistema.login.service.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.login.model.Role;

@Repository
public interface RolePersistence extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
