package com.sistema.login.service.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.login.model.User;

@Repository
public interface UserPersistence extends JpaRepository<User, Long> {

    User findByEmail(String email);

}