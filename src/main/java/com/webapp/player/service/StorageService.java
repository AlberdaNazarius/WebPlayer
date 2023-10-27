package com.webapp.player.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
  byte[] downloadImage(final String fileName);
  void uploadImage(final MultipartFile file);
}
