package com.example.sellservicespringproject.services;

import com.example.sellservicespringproject.models.dtos.CategoryDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    ResponseEntity<?> saveCategory(String token, CategoryDto categoryDto);

    ResponseEntity<?> getByName(String name);

    List<CategoryDto> getAllCategories();
}
