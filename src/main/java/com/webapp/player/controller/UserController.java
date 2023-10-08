package com.webapp.player.controller;

import com.webapp.player.dto.UserDto;
import com.webapp.player.service.UserService;
import com.webapp.player.service.mapper.UserMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
  private final UserService userService;
  private final UserMapper userMapper;

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUser(@PathVariable @Nonnull final Long id) {
    final var user = userService.getUserById(id);
    final var response = userMapper.toUserDto(user);
    log.info("User with id {} was received", id);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<UserDto> addUser(@RequestBody @Nonnull final UserDto userDto) {
    final var userToAdd = userMapper.toUser(userDto);
    final var user = userService.addUser(userToAdd);
    final var response = userMapper.toUserDto(user);
    log.info("User with id {} was created", user.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
