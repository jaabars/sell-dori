package com.example.sellservicespringproject.services;

import com.example.sellservicespringproject.models.entities.Code;
import com.example.sellservicespringproject.models.entities.User;

public interface CodeService {

    int randomCode();

    void saveCode(Code code);

    Code findUserCode(User user);
}
