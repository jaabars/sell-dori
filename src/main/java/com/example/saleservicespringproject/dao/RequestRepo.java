package com.example.saleservicespringproject.dao;

import com.example.saleservicespringproject.models.entities.Code;
import com.example.saleservicespringproject.models.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepo extends JpaRepository<Request, Long> {

    int countByCodeAndSuccess(Code code, boolean value);

}
