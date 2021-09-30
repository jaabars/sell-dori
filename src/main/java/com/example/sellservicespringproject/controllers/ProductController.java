package com.example.sellservicespringproject.controllers;

<<<<<<< HEAD
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    /**
     *C
     * R
     * U
     * D
     *
     */

}
=======
import com.example.sellservicespringproject.models.dtos.ProductDto;
import com.example.sellservicespringproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vq/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestHeader String token, @RequestBody ProductDto productDto) {
        return productService.saveProduct(token, productDto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestHeader String token, @RequestBody ProductDto productDto) {
        return productService.saveProduct(token, productDto);
    }

    @GetMapping("/getByBarcode")
    public ResponseEntity<?> getProductByBarcode (@RequestHeader String token, @RequestParam String barcode) {
        return productService.getProductByBarcode(token, barcode);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts(@RequestHeader String token) {
        return productService.getAllProducts(token);
    }
}
>>>>>>> 4b30d6ee2da6934bb663720aac9455c2ac9d45d2
