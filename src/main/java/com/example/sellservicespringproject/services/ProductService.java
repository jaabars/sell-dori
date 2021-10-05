package com.example.sellservicespringproject.services;

import com.example.sellservicespringproject.models.dtos.ProductDto;
import org.springframework.http.ResponseEntity;

public interface ProductService {

    ResponseEntity<?> saveProduct(String token, ProductDto productDto);

    ResponseEntity<?> getProductByBarcode(String token, String barcode);

    ResponseEntity<?> getAllProducts(String token);

    ProductDto findProductByBarcodeForOperationDetails(String barcode);

    ResponseEntity<?> updateProduct(String token, ProductDto productDto);
}