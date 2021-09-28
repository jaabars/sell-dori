package com.example.sellservicespringproject.controllers;

import com.example.sellservicespringproject.models.dtos.CategoryDto;
import com.example.sellservicespringproject.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCategory (@RequestHeader String token, @RequestBody CategoryDto categoryDto) {
        return categoryService.saveCategory(token, categoryDto);
    }

    @GetMapping("/getByName")
    public ResponseEntity<?> getByName (@RequestParam String name) {
        return categoryService.getByName(name);
    }

    @GetMapping("/getAllCategories")
    public List<CategoryDto> getAllCategories () {
        return categoryService.getAllCategories();
    }
}