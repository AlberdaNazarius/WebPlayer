package com.webapp.player.service.imp;

import com.webapp.player.common.error.UserNotFoundException;
import com.webapp.player.dto.SignupRequest;
import com.webapp.player.dto.UserDto;
import com.webapp.player.persistence.entity.Role;
import com.webapp.player.persistence.entity.User;
import com.webapp.player.persistence.repository.RoleRepository;
import com.webapp.player.persistence.repository.UserRepository;
import com.webapp.player.service.UserService;
import com.webapp.player.service.mapper.UserMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  @Override
  @Transactional(readOnly = true)
  public User getUserById(@Nonnull final Long id) {
    return userRepository.getReferenceById(id);
  }

  @Override
  @Transactional
  public User addUser(@Nonnull final User user) {
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User modifyUser(@Nonnull final Long userId, @Nonnull final UserDto userDto) {
    final var user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new UserNotFoundException("User with such id was not found");
    }
    userMapper.updateUser(user.get(), userDto);
    return userRepository.save(user.get());
  }

  @Override
  @Transactional
  public User deleteUser(@Nonnull final Long id) {
    final var userToDelete = userRepository.findById(id);
    if (userToDelete.isEmpty()) {
      throw new UserNotFoundException("User with such id was not found");
    }
    userRepository.delete(userToDelete.get());
    return userToDelete.get();
  }

  @Override
  @Transactional
  public ResponseEntity<?> registerUser(SignupRequest signupRequest) {
    if (userRepository.existsByUsername(signupRequest.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body("Error: Username is already taken!");
    }

    User user = new User();
    user.setUsername(signupRequest.getUsername());
    user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

    Role userRole = roleRepository.findByName("USER");
    user.getRoles().add(userRole);

    userRepository.save(user);

    return ResponseEntity.ok("User registered successfully!");
  }

//  TODO maybe remove this
  @Override
  @Transactional
  public ResponseEntity<?> loginUser(SignupRequest loginRequest) {
    final var existingUser = userRepository.existsByUsername(loginRequest.getUsername());
    if (!existingUser) {
      throw new UserNotFoundException("User with such name was not found");
    }

    return ResponseEntity.ok("User log in was successful!");
  }
}
