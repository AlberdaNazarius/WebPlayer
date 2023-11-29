package com.webapp.player.controller;

import com.webapp.player.dto.PlaylistDto;
import com.webapp.player.dto.PlaylistUploadDto;
import com.webapp.player.service.PlaylistService;
import com.webapp.player.service.mapper.PlaylistMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping("/playlists")
  public List<PlaylistDto> getAllPlaylists() {
    return playlistService.getPlaylists().stream().map(playlistMapper::toPlaylistDto).toList();
  }

  @PostMapping
  public PlaylistDto addPlaylist(@RequestBody @Nonnull final PlaylistUploadDto dto) {
    final var playlistToAdd = playlistMapper.toPlaylist(dto);
    final var playlist = playlistService.addPlaylist(playlistToAdd, dto.getImageName());
    return playlistMapper.toPlaylistDto(playlist);
  }
}
