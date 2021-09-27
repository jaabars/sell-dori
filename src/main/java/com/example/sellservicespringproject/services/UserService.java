package com.example.sellservicespringproject.services;

import com.example.sellservicespringproject.models.dtos.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> saveUser(UserDto userDto);

    ResponseEntity<?> sendCode(String login);

    ResponseEntity<?> getToken(String login, String code);
}
