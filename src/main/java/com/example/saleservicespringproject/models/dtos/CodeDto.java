package com.example.saleservicespringproject.models.dtos;

import com.example.saleservicespringproject.models.enums.CodeStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodeDto {

    Long id;
    int code;
    Date startDate;
    Date endDate;
    CodeStatus codeStatus;
    UserDto user;
}