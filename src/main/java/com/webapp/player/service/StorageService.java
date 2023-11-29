package com.webapp.player.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
  byte[] downloadFile(final String fileName);
  void uploadFile(final MultipartFile file);
}
