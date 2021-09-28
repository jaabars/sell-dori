package com.example.sellservicespringproject.mappers;

import com.example.sellservicespringproject.models.dtos.CategoryDto;
import com.example.sellservicespringproject.models.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category mapToCategory(CategoryDto categoryDto);

    CategoryDto mapToCategoryDto(Category category);
}
