package com.webapp.player.service.imp;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.webapp.player.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.webapp.player.common.util.FileUtils.convertMultiPartFileToFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImp implements StorageService {

  @Value("${application.bucket.name}")
  private String bucketName;

  private final AmazonS3 s3Client;

  @Override
  @Transactional
  public byte[] downloadFile(final String fileName) {
    S3Object s3Object = s3Client.getObject(bucketName, fileName);
    S3ObjectInputStream inputStream = s3Object.getObjectContent();
    try {
      return IOUtils.toByteArray(inputStream);
    } catch (IOException e) {
      log.error("While downloading file the following error occurred", e);
    }
    return null;
  }

  @Override
  @Transactional
  public void uploadFile(final MultipartFile file) {
    File fileObj = convertMultiPartFileToFile(file);
    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
    fileObj.delete();
    log.info("File uploaded : " + fileName);
  }
}
