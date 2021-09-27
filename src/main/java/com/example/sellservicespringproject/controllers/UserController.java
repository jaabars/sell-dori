package com.example.sellservicespringproject.controllers;

import com.example.sellservicespringproject.models.dtos.UserDto;
import com.example.sellservicespringproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @PostMapping("/sendCode")
    public ResponseEntity<?> sendCode(@RequestParam String login) {
        return userService.sendCode(login);
    }

    @GetMapping("/login")
    public ResponseEntity<?> getToken(@RequestParam String login, @RequestParam String code) {
        return userService.getToken(login, code);
    }
}