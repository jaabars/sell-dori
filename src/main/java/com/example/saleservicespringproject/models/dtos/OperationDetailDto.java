package com.example.saleservicespringproject.models.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OperationDetailDto {

    Long id;
    ProductDto product;
    OperationDto operation;
    int number;
    double price;
}
