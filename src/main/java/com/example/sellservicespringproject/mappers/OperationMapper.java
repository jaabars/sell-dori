package com.example.sellservicespringproject.mappers;

import com.example.sellservicespringproject.models.dtos.OperationDto;
import com.example.sellservicespringproject.models.entities.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperationMapper {

    OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);

    Operation mapToOperation(OperationDto operationDto);

    OperationDto mapToOperationDto(Operation operation);
}