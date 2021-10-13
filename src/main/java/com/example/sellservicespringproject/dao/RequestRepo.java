package com.example.sellservicespringproject.dao;

import com.example.sellservicespringproject.models.entities.Code;
import com.example.sellservicespringproject.models.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends JpaRepository<Request, Long> {

    int countByCodeAndSuccess(Code code, boolean success);
}