package com.example.sellservicespringproject.services.impl;

import com.example.sellservicespringproject.dao.RequestRepo;
import com.example.sellservicespringproject.mappers.CodeMapper;
import com.example.sellservicespringproject.models.dtos.CodeDto;
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
    public void saveRequest(CodeDto checkUserCode, boolean value) {

        Request saveRequest = new Request();
        saveRequest
                .setCode(
                        CodeMapper
                                .INSTANCE
                                .mapToCode(checkUserCode));
        saveRequest.setSuccess(value);
        requestRepo.save(saveRequest);
    }

    @Override
    public int countFailedAttempts(CodeDto codeDto) {

        return requestRepo
                .countByCodeAndSuccess(
                        CodeMapper
                                .INSTANCE
                                .mapToCode(codeDto)
                        , false);
    }
}
