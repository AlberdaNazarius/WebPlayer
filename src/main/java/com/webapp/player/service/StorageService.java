package com.webapp.player.service;

import com.webapp.player.persistence.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
  Image downloadImage(final String fileName);
  void uploadImage(final MultipartFile file);
}
