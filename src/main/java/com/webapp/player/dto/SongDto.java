package com.webapp.player.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SongDto {
  String name;
  String author;
  Date addedDate;
  String duration;
  String imageKey;
}