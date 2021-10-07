package com.example.sellservicespringproject.services;

import com.example.sellservicespringproject.models.dtos.DiscountDto;
import com.example.sellservicespringproject.models.dtos.ProductDto;
import org.springframework.http.ResponseEntity;

public interface DiscountService {

    ResponseEntity<?> saveDiscount(String token, DiscountDto discountDto);

    ResponseEntity<?> findDiscountByProduct(String token, ProductDto productDto);

    ResponseEntity<?> getAllDiscounts(String token);

    double getDiscountByProduct(ProductDto productDto);
}