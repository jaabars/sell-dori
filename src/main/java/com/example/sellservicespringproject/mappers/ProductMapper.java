package com.example.sellservicespringproject.mappers;

import com.example.sellservicespringproject.models.dtos.ProductDto;
import com.example.sellservicespringproject.models.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    
    Product mapToProduct(ProductDto productDto);
    
    ProductDto mapToProductDto(Product product);
}