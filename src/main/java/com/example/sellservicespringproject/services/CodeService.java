package com.example.sellservicespringproject.services;

import com.example.sellservicespringproject.models.dtos.CodeDto;
import com.example.sellservicespringproject.models.dtos.UserDto;
import com.example.sellservicespringproject.models.entities.Code;

public interface CodeService {

    void saveCode(CodeDto codeDto);

    Code findLastCode(UserDto userDto);

    void sendCode(UserDto userDto);
}