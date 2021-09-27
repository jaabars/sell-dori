package com.example.sellservicespringproject.dao;

import com.example.sellservicespringproject.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByLogin(String login);
}