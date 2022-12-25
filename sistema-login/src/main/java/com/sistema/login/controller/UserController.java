package com.sistema.login.controller;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.login.model.User;
import com.sistema.login.service.UserLocalService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject register(@RequestBody User user) {
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

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserLocalService userLocalService;

}
