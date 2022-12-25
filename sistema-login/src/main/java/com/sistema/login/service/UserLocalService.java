package com.sistema.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.login.model.User;
import com.sistema.login.service.persistence.UserPersistence;

@Service
public class UserLocalService {

    public User findByEmail(String email) {
        return userPersistence.findByEmail(email);
    }

    public User save(User user) {
        return userPersistence.save(user);
    }

    @Autowired
    private UserPersistence userPersistence;

}
