package com.example.saleservicespringproject.services;

import com.example.saleservicespringproject.models.dtos.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> saveUser(UserDto userDto);

    ResponseEntity<?> sendCode(String login);

    ResponseEntity<?> getToken(String login, String code);
}
