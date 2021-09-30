package com.example.sellservicespringproject.mappers;

import com.example.sellservicespringproject.models.dtos.DiscountDto;
import com.example.sellservicespringproject.models.entities.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiscountMapper {

    DiscountMapper INSTANCE = Mappers.getMapper(DiscountMapper.class);

    Discount mapToDiscount(DiscountDto discountDto);

    DiscountDto mapToDiscountDto(Discount discount);
}
