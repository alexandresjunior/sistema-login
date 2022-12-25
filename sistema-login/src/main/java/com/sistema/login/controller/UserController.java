package com.sistema.login.controller;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.sistema.login.configuration.jwt.JwtTokenProvider;
import com.sistema.login.constants.UserConstants;
import com.sistema.login.model.User;
import com.sistema.login.service.RoleLocalService;
import com.sistema.login.service.UserLocalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleLocalService.findByName(UserConstants.ADMIN.toString()));

        user = userLocalService.save(user);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("message", user.getName() + " registered succesfully!");
            jsonObject.put("status", HttpStatus.OK);
        } catch (JSONException e) {
            log.error("Error registering user: ", e.getMessage());
        }

        return jsonObject;
    }

    // @PostMapping(value = "/register", produces =
    // MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<String> register(@RequestBody User user) {
    // log.info("UserController : register");

    // JSONObject jsonObject = new JSONObject();

    // try {
    // user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    // user.setRole(roleLocalService.findByName(UserConstants.USER.toString()));

    // User savedUser = userLocalService.save(user);

    // jsonObject.put("message", savedUser.getName() + " saved succesfully");

    // return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    // } catch (JSONException e) {
    // try {
    // jsonObject.put("exception", e.getMessage());
    // } catch (JSONException e1) {
    // e1.printStackTrace();
    // }

    // return new ResponseEntity<String>(jsonObject.toString(),
    // HttpStatus.UNAUTHORIZED);
    // }
    // }

    // @PostMapping(value = "/authenticate", produces =
    // MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<String> authenticate(@RequestBody User user) {
    // log.info("UserController : authenticate");

    // JSONObject jsonObject = new JSONObject();

    // try {
    // Authentication authentication = authenticationManager
    // .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),
    // user.getPassword()));

    // if (authentication.isAuthenticated()) {
    // String email = user.getEmail();

    // jsonObject.put("name", authentication.getName());
    // jsonObject.put("authorities", authentication.getAuthorities());
    // jsonObject.put("token",
    // jwtTokenProvider.createToken(email,
    // userLocalService.findByEmail(email).getRole()));

    // return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
    // }
    // } catch (JSONException e) {
    // try {
    // jsonObject.put("exception", e.getMessage());
    // } catch (JSONException e1) {
    // e1.printStackTrace();
    // }

    // return new ResponseEntity<String>(jsonObject.toString(),
    // HttpStatus.UNAUTHORIZED);
    // }

    // return null;
    // }

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    private final PasswordEncoder passwordEncoder;

    // @Autowired
    // private AuthenticationManager authenticationManager;

    // @Autowired
    // private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RoleLocalService roleLocalService;

    @Autowired
    private UserLocalService userLocalService;

}
