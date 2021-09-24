package com.example.saleservicespringproject.dao;

import com.example.saleservicespringproject.models.entities.Code;
import com.example.saleservicespringproject.models.entities.User;
import com.example.saleservicespringproject.models.enums.CodeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepo extends JpaRepository<Code, Long > {

    Code findByUserAndCodeStatus(User user, CodeStatus status);

}
