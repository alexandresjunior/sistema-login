package com.sistema.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    public User loadUserByUsername(String username) {
        User user = userPersistence.findByEmail(username);

        String[] roles = user.getRole().getName().equals("ADMIN") ? new String[] { "ADMIN", "GUEST" }
                : new String[] { "GUEST" };

        return User
                .builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public UserDetails autenticar(User user) {
        UserDetails userDetails = loadUserByUsername(user.getEmail());

        boolean passwordsMatch = passwordEncoder.matches(user.getPassword(), userDetails.getPassword());

        if (passwordsMatch) {
            return userDetails;
        }

        return null;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserPersistence userPersistence;

}
