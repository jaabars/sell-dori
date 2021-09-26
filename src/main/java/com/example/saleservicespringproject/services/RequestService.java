package com.example.saleservicespringproject.services;

import com.example.saleservicespringproject.models.entities.Code;
import com.example.saleservicespringproject.models.entities.Request;

public interface RequestService {

    void saveRequest(Request request);

    int countFailedAttempts(Code code);
}
