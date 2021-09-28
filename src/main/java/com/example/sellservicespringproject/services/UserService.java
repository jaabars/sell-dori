package com.example.sellservicespringproject.services;

import com.example.sellservicespringproject.models.dtos.UserDto;
import com.example.sellservicespringproject.models.entities.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> saveUser(UserDto userDto);

    ResponseEntity<?> sendCode(String login);

    ResponseEntity<?> getToken(String login, String code);

    ResponseEntity<?> verifyLogin(String token);

    boolean userLockOutChecking(User user);
}