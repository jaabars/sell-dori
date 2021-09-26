package com.example.saleservicespringproject.services.impl;

import com.example.saleservicespringproject.dao.RequestRepo;
import com.example.saleservicespringproject.models.entities.Code;
import com.example.saleservicespringproject.models.entities.Request;
import com.example.saleservicespringproject.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepo requestRepo;

    @Override
    public void saveRequest(Request request) {
        requestRepo.save(request);
    }

    @Override
    public int countFailedAttempts(Code code) {
        return requestRepo.countByCodeAndSuccess(code, false);
    }
}
