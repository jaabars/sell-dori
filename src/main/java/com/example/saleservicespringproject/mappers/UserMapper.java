package com.example.saleservicespringproject.mappers;

import com.example.saleservicespringproject.models.dtos.UserDto;
import com.example.saleservicespringproject.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapToUser(UserDto userDto);

    UserDto mapToUserDto(User user);
}