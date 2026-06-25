package com.testify.testify.mapper;

import com.testify.testify.dto.UserDto;
import com.testify.testify.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);
}
