package com.webapp.player.controller;

import com.webapp.player.dto.SongDto;
import com.webapp.player.service.SongService;
import com.webapp.player.service.mapper.SongMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/song")
@RequiredArgsConstructor
public class SongController {
  private final SongService songService;
  private final SongMapper songMapper;

  @GetMapping("/songs")
  public List<SongDto> getAllSongs() {
    return songService.getSongs().stream().map(songMapper::toSongDto).toList();
  }

  @PostMapping
  public SongDto addSong(@RequestBody @Nonnull final SongDto dto) {
    final var songToAdd = songMapper.toSong(dto);
    final var song = songService.addSong(songToAdd);
    return songMapper.toSongDto(song);
  }

  @DeleteMapping("/{id}")
  public SongDto deleteSong(@PathVariable final Long id) {
    final var response = songService.deleteSong(id);
    return songMapper.toSongDto(response);
  }
}
