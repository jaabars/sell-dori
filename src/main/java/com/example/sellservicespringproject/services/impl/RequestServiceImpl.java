package com.example.sellservicespringproject.services.impl;

import com.example.sellservicespringproject.dao.RequestRepo;
import com.example.sellservicespringproject.models.entities.Code;
import com.example.sellservicespringproject.models.entities.Request;
import com.example.sellservicespringproject.services.RequestService;
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
