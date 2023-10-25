package com.webapp.player.controller;

import com.webapp.player.service.SongService;
import com.webapp.player.service.mapper.SongMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/song")
@RequiredArgsConstructor
public class SongController {
  private final SongService songService;
  private final SongMapper songMapper;
}
