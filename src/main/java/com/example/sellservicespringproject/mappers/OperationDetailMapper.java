package com.example.sellservicespringproject.mappers;

import com.example.sellservicespringproject.models.dtos.OperationDetailDto;
import com.example.sellservicespringproject.models.entities.OperationDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperationDetailMapper {

    OperationDetailMapper INSTANCE = Mappers.getMapper(OperationDetailMapper.class);

    OperationDetail mapToOperationDetail(OperationDetailDto operationDetailDto);

    OperationDetailDto mapToOperationDetailDto(OperationDetail operationDetail);
}
