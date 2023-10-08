package com.webapp.player.service.mapper;

import com.webapp.player.dto.UserDto;
import com.webapp.player.persistence.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toUserDto(User user);
  User toUser(UserDto userDto);
  void updateUser(@MappingTarget User user, UserDto userDto);
}
