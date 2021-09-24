package com.example.saleservicespringproject.services.impl;

import com.example.saleservicespringproject.dao.CodeRepo;
import com.example.saleservicespringproject.models.entities.Code;
import com.example.saleservicespringproject.models.entities.User;
import com.example.saleservicespringproject.models.enums.CodeStatus;
import com.example.saleservicespringproject.services.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    private CodeRepo codeRepo;

    @Override
    public int randomCode() {
        return (int) (Math.random() * 9000) + 1000;
    }

    @Override
    public void saveCode(Code code) {
        codeRepo.save(code);
    }

    @Override
    public Code findUserCode(User user) {
        return codeRepo.findByUserAndCodeStatus(user, CodeStatus.NEW);
    }
}
