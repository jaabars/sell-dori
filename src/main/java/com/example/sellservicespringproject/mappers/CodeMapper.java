package com.example.sellservicespringproject.mappers;

import com.example.sellservicespringproject.models.dtos.CodeDto;
import com.example.sellservicespringproject.models.entities.Code;
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
