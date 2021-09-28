package com.example.sellservicespringproject.dao;

import com.example.sellservicespringproject.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    Category findByName(String name);
}