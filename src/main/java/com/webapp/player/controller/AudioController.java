package com.webapp.player.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping("/api/audio")
@RequiredArgsConstructor
public class AudioController {
  @GetMapping("/stream/{filename}")
  public ResponseEntity<Resource> streamAudio(
          @PathVariable String filename,
          @RequestHeader(value = "Range", required = false) String rangeHeader
  ) {
    try {
      Path filePath = Paths.get("audio-storage", filename).toAbsolutePath();

      if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
        return ResponseEntity.notFound().build();
      }

      long fileSize = Files.size(filePath);
      long rangeStart = 0;
      long rangeEnd = fileSize - 1;

      if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
        String[] ranges = rangeHeader.substring(6).split("-");
        try {
          rangeStart = Long.parseLong(ranges[0]);
          if (ranges.length > 1 && !ranges[1].isEmpty()) {
            rangeEnd = Long.parseLong(ranges[1]);
          }
          rangeEnd = Math.min(rangeEnd, fileSize - 1);
        } catch (NumberFormatException e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
      }

      long contentLength = rangeEnd - rangeStart + 1;

      InputStream inputStream = Files.newInputStream(filePath, StandardOpenOption.READ);
      inputStream.skip(rangeStart);

      return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
              .contentType(MediaType.parseMediaType("audio/mpeg"))
              .header("Accept-Ranges", "bytes")
              .header("Content-Length", String.valueOf(contentLength))
              .header("Content-Range",
                      String.format("bytes %d-%d/%d", rangeStart, rangeEnd, fileSize))
              .body(new InputStreamResource(inputStream));

    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}