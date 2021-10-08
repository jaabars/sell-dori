package com.example.sellservicespringproject.dao;

import com.example.sellservicespringproject.models.entities.Code;
import com.example.sellservicespringproject.models.entities.User;
import com.example.sellservicespringproject.models.enums.CodeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepo extends JpaRepository<Code, Long > {

    Code findByUserAndCodeStatus(User user, CodeStatus status);
}