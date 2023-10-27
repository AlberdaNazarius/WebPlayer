package com.webapp.player.controller;

import com.webapp.player.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
  private final StorageService storageService;

  @GetMapping("/{fileName}")
  public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
    final var imageToDownload = storageService.downloadImage(fileName);
    return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.valueOf(imageToDownload.getType()))
            .body(imageToDownload.getImageData());
  }

  @PostMapping
  public void uploadImage(@RequestParam(value = "image", required = false) MultipartFile file) {
    storageService.uploadImage(file);
  }
}
