package com.webapp.player.common.error;

public class PlaylistNotFoundException extends RuntimeException {
  public PlaylistNotFoundException(String message) {
    super(message);
  }
}
