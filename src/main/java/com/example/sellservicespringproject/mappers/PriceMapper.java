package com.example.sellservicespringproject.mappers;

import com.example.sellservicespringproject.models.dtos.PriceDto;
import com.example.sellservicespringproject.models.entities.Price;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PriceMapper {

    PriceMapper INSTANCE = Mappers.getMapper(PriceMapper.class);

    Price mapToPrice(PriceDto priceDto);

    PriceDto mapToPriceDto(Price price);
}