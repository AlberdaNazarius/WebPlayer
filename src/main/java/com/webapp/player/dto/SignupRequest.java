package com.webapp.player.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupRequest {
  @NotBlank
  String username;

  @NotBlank
  @Size(min = 6, max = 40)
  String password;
}
