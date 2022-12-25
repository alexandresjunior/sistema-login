package com.sistema.login.configuration.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sistema.login.model.User;
import com.sistema.login.service.UserLocalService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

@Service
public class JwtTokenProvider {

    public String createToken(User user) {
        long expTime = Long.valueOf(expirationTimeInSeconds);

        LocalDateTime expDateTime = LocalDateTime.now().plusSeconds(expTime);

        Instant instant = expDateTime.atZone(ZoneId.systemDefault()).toInstant();

        Date expDate = Date.from(instant);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        Claims claims = getClaimsFromToken(token);

        Date expDate = claims.getExpiration();

        LocalDateTime expDateTime = expDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return !LocalDateTime.now().isAfter(expDateTime);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String username) {
        UserDetails userDetails = userLocalService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration.time.seconds}")
    private String expirationTimeInSeconds;

    @Autowired
    private UserLocalService userLocalService;

}
