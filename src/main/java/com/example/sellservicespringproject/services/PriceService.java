package com.example.sellservicespringproject.services;

import com.example.sellservicespringproject.models.dtos.PriceDto;
import com.example.sellservicespringproject.models.dtos.ProductDto;
import org.springframework.http.ResponseEntity;

public interface PriceService {

    ResponseEntity<?> savePrice(String token, PriceDto priceDto);

    ResponseEntity<?> getPriceByProduct(String token, ProductDto productDto);

    ResponseEntity<?> getAllPrices(String token);

    double findPriceByProductForOperationDetails(ProductDto productDto);
}