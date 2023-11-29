package com.webapp.player.controller;

import com.webapp.player.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class StorageController {

  private final StorageService service;

  @GetMapping("/download/{fileName}")
  public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
    byte[] data = service.downloadFile(fileName);
    ByteArrayResource resource = new ByteArrayResource(data);
    return ResponseEntity
            .ok()
            .contentLength(data.length)
            .header("Content-type", "application/octet-stream")
            .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
            .body(resource);
  }

  @PostMapping("/upload")
  public ResponseEntity<?> uploadFile(@RequestParam(value = "file") MultipartFile file) {
    service.uploadFile(file);
    return ResponseEntity.ok().build();
  }

//  @DeleteMapping("/delete/{fileName}")
//  public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
//    return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
//  }
}