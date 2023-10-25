package com.webapp.player.common.error;

public class SongNotFoundException extends RuntimeException{
  public SongNotFoundException(String message) {
    super(message);
  }
}
