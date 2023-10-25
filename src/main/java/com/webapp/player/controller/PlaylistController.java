package com.webapp.player.controller;

import com.webapp.player.dto.PlaylistDto;
import com.webapp.player.service.PlaylistService;
import com.webapp.player.service.mapper.PlaylistMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
public class PlaylistController {
  private final PlaylistService playlistService;
  private final PlaylistMapper playlistMapper;

  @GetMapping("/{id}")
  public PlaylistDto getPlaylist(@PathVariable @Nonnull final Long id) {
    final var playlist = playlistService.getPlaylistById(id);
    return playlistMapper.toPlaylistDto(playlist);
  }

  @PostMapping
  public PlaylistDto addPlaylist(@RequestBody @Nonnull final PlaylistDto playlistDto) {
    final var playlistToAdd = playlistMapper.toPlaylist(playlistDto);
    final var playlist = playlistService.addPlaylist(playlistToAdd);
    return playlistMapper.toPlaylistDto(playlist);
  }
}
