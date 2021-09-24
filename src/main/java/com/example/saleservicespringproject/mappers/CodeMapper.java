package com.example.saleservicespringproject.mappers;

import com.example.saleservicespringproject.models.dtos.CodeDto;
import com.example.saleservicespringproject.models.entities.Code;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CodeMapper {

    CodeMapper INSTANCE = Mappers.getMapper(CodeMapper.class);

    @Mapping(source = "user.id", target = "user.id")
    Code mapToCode(CodeDto codeDto);

    @Mapping( source = "user.id", target = "user.id")
    CodeDto mapToCodeDto(Code code);
}
