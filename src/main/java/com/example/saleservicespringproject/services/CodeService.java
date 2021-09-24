package com.example.saleservicespringproject.services;

import com.example.saleservicespringproject.models.entities.Code;
import com.example.saleservicespringproject.models.entities.User;

public interface CodeService {

    int randomCode();

    void saveCode(Code code);

    Code findUserCode(User user);
}
