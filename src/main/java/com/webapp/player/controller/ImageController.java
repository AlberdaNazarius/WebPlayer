package com.webapp.player.controller;

import com.webapp.player.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
  private final StorageService storageService;

  @PostMapping
  public void uploadImage(@RequestParam(value = "image", required = false) MultipartFile file) {
    storageService.uploadImage(file);
  }
}
