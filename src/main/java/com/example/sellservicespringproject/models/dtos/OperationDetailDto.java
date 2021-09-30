package com.example.sellservicespringproject.models.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OperationDetailDto {

    Long id;
    ProductDto product;
    OperationDto operation;
    int quantity;
    double amount;
}