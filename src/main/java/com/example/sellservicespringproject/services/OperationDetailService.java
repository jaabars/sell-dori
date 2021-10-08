package com.example.sellservicespringproject.services;

import com.example.sellservicespringproject.models.dtos.OperationDetailDto;

import java.util.List;

public interface OperationDetailService {

    void saveOperationDetails(List<OperationDetailDto> operationDetailDtoList);
}