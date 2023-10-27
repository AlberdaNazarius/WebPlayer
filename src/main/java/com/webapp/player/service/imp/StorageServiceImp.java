package com.webapp.player.service.imp;

import com.webapp.player.common.util.ImageUtils;
import com.webapp.player.persistence.entity.Image;
import com.webapp.player.persistence.repository.ImageRepository;
import com.webapp.player.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImp implements StorageService {
  private final ImageRepository imageRepository;

  public Image downloadImage(String fileName) {
    final var retrievedImage = imageRepository.findByName(fileName);
    final var decompressImageData = ImageUtils.decompressImage(retrievedImage.get().getImageData());
    retrievedImage.get().setImageData(decompressImageData);
    log.info("File {} was successfully downloaded", fileName);
    return retrievedImage.get();
  }

  public void uploadImage(final MultipartFile file) {
    final Image imageData;
    try {
      imageData = Image.builder()
              .name(file.getOriginalFilename())
              .type(file.getContentType())
              .imageData(ImageUtils.compressImage(file.getBytes()))
              .build();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    imageRepository.save(imageData);
    log.info("File {} was successfully uploaded", imageData.getName());
  }
}
