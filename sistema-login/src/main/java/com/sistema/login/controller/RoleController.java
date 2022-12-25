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

import com.sistema.login.model.Role;
import com.sistema.login.service.RoleLocalService;

@RestController
@RequestMapping("/role")
@CrossOrigin(origins = "http://localhost:3000")
public class RoleController {

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject register(@RequestBody Role role) {
        role = roleLocalService.save(role);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("message", role.getName() + " registered succesfully!");
            jsonObject.put("status", HttpStatus.OK);
        } catch (JSONException e) {
            log.error("Error registering role: ", e.getMessage());
        }

        return jsonObject;
    }

    private static Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleLocalService roleLocalService;

}
