package com.example.sellservicespringproject.services;

import com.example.sellservicespringproject.models.dtos.CodeDto;

public interface RequestService {

    void saveRequest(CodeDto codeDto, boolean value);

    int countFailedAttempts(CodeDto codeDto);
}