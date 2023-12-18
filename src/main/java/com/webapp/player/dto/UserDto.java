package com.webapp.player.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
  Long id;
  String nickname;
  String imageKey;
  Set<PlaylistDto> playlists;
}
