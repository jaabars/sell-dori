package com.example.sellservicespringproject.controllers;

import com.example.sellservicespringproject.models.dtos.DiscountDto;
import com.example.sellservicespringproject.models.dtos.ProductDto;
import com.example.sellservicespringproject.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping("/save")
    public ResponseEntity<?> saveDiscount(@RequestHeader String token, @RequestBody DiscountDto discountDto) {
        return discountService.saveDiscount(token, discountDto);
    }

    @PostMapping("getByProduct")
    public ResponseEntity<?> getByProduct(@RequestHeader String token, @RequestBody ProductDto productDto) {
        return discountService.getDiscountByProduct(token, productDto);
    }

    @GetMapping("/getAllDiscounts")
    public ResponseEntity<?> getAllDiscounts(@RequestHeader String token) {
        return discountService.getAllDiscounts(token);
    }
}