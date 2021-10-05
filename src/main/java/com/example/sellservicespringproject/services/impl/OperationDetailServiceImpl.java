package com.example.sellservicespringproject.services.impl;

import com.example.sellservicespringproject.dao.OperationDetailRepo;
import com.example.sellservicespringproject.mappers.OperationDetailMapper;
import com.example.sellservicespringproject.models.dtos.OperationDetailDto;
import com.example.sellservicespringproject.services.OperationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationDetailServiceImpl implements OperationDetailService {

    @Autowired
    private OperationDetailRepo operationDetailRepo;

    @Override
    public void saveOperationDetails(List<OperationDetailDto> operationDetailDtoList) {

        for (OperationDetailDto element: operationDetailDtoList) {

            operationDetailRepo
                    .save(
                            OperationDetailMapper
                                    .INSTANCE
                                    .mapToOperationDetail(element)
                    );
        }
    }
}