package com.webapp.player.service;

import com.webapp.player.dto.UserDto;
import com.webapp.player.persistence.entity.User;

public interface UserService {
  User getUserById(final Long id);
  User addUser(final User user);
  User modifyUser(final Long userId, final UserDto userDto);
  User deleteUser(final Long id);
}
