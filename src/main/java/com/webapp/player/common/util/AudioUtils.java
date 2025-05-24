package com.webapp.player.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@UtilityClass
@Slf4j
public class AudioUtils {
  public static MediaType detectMediaType(String filename) {
    String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    return switch (extension) {
      case "mp3" -> MediaType.parseMediaType("audio/mpeg");
      case "wav" -> MediaType.parseMediaType("audio/wav");
      case "ogg" -> MediaType.parseMediaType("audio/ogg");
      default -> MediaType.APPLICATION_OCTET_STREAM;
    };
  }
}
