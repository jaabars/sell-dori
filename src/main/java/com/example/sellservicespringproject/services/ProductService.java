package com.example.sellservicespringproject.services;

import com.example.sellservicespringproject.models.dtos.ProductDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ResponseEntity<?> saveProduct(String token, ProductDto productDto);

    ResponseEntity<?> getProductByBarcode(String token, String barcode);

    List<ProductDto> getAllProducts(String token);
}