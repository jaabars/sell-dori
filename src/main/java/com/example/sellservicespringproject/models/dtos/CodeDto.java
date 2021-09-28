package com.example.sellservicespringproject.models.dtos;

import com.example.sellservicespringproject.models.enums.CodeStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodeDto {

    Long id;
    String code;
    Date startDate;
    Date endDate;
    CodeStatus codeStatus;
    UserDto user;
}