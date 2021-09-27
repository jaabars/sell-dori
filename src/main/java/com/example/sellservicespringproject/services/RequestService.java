package com.example.sellservicespringproject.services;

import com.example.sellservicespringproject.models.entities.Code;
import com.example.sellservicespringproject.models.entities.Request;

public interface RequestService {

    void saveRequest(Request request);

    int countFailedAttempts(Code code);
}
