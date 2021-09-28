package com.example.sellservicespringproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class DiscountController {


    @PostMapping("/save")
    public ResponseEntity<?> saveDiscount(@RequestHeader String token){
        return null;
    }
    ///CRUD
}
