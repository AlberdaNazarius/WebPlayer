package com.webapp.player.controller;

import com.webapp.player.dto.SignupRequest;
import com.webapp.player.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
    log.info("Sign up request received with parameters {}", signupRequest);
    return userService.registerUser(signupRequest);
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody SignupRequest request) {
    try {
      Authentication auth = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getUsername(),
                      request.getPassword()
              )
      );

      SecurityContextHolder.getContext().setAuthentication(auth);
      return ResponseEntity.ok("Logged in successfully");
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(401).body("Invalid credentials");
    }
  }
}
