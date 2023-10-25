package com.webapp.player.dto;

import com.webapp.player.persistence.entity.Song;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistDto {
  String name;
  String image;
  Set<Song> songs;
}
