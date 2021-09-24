package com.example.saleservicespringproject.dao;

import com.example.saleservicespringproject.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByLogin(String login);
}