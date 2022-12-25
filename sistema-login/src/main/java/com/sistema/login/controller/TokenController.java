package com.sistema.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sistema.login.configuration.jwt.JwtTokenProvider;
import com.sistema.login.dto.CredentialsDTO;
import com.sistema.login.dto.TokenDTO;
import com.sistema.login.model.User;
import com.sistema.login.service.UserLocalService;

@RestController
public class TokenController {
    
    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredentialsDTO credentialsDTO) {
        try {
            User user = User.builder()
                    .email(credentialsDTO.getUsername())
                    .password(credentialsDTO.getPassword()).build();

            UserDetails userAuthenticated = userLocalService.autenticar(user);
            
            String token = jwtTokenProvider.createToken(userAuthenticated);

            return new TokenDTO(userAuthenticated.getLogin(), token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserLocalService userLocalService;
    
}
