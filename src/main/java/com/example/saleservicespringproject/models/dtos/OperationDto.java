package com.example.saleservicespringproject.models.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OperationDto {

    Long id;
    Date startDate;
    Date endDate;
    double totalPrice;
    double change;
    UserDto user;
    double cash;
}