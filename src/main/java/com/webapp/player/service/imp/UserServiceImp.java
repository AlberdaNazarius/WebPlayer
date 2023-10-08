package com.webapp.player.service.imp;

import com.webapp.player.common.error.UserNotFoundException;
import com.webapp.player.dto.UserDto;
import com.webapp.player.persistence.entity.User;
import com.webapp.player.persistence.repository.UserRepository;
import com.webapp.player.service.UserService;
import com.webapp.player.service.mapper.UserMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public User getUserById(@Nonnull final Long id) {
    return userRepository.getReferenceById(id);
  }

  @Override
  public User addUser(@Nonnull final User user) {
    return userRepository.save(user);
  }

  @Override
  public User modifyUser(@Nonnull final Long userId, @Nonnull final UserDto userDto) {
    final var user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new UserNotFoundException("User with such id was not found");
    }
    userMapper.updateUser(user.get(), userDto);
    return userRepository.save(user.get());
  }

  @Override
  public User deleteUser(@Nonnull final Long id) {
    final var userToDelete = userRepository.findById(id);
    if (userToDelete.isEmpty()) {
      throw new UserNotFoundException("User with such id was not found");
    }
    userRepository.delete(userToDelete.get());
    return userToDelete.get();
  }
}
