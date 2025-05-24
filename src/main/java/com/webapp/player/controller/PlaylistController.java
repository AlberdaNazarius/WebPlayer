package com.webapp.player.controller;

import com.webapp.player.dto.PlaylistDto;
import com.webapp.player.service.PlaylistService;
import com.webapp.player.service.mapper.PlaylistMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
@Slf4j
public class PlaylistController {
  private final PlaylistService playlistService;
  private final PlaylistMapper playlistMapper;

  @GetMapping("/{id}")
  public PlaylistDto getPlaylist(@PathVariable @Nonnull final Long id) {
    final var playlist = playlistService.getPlaylistById(id);
    log.info("GET: Playlist with id {} was received", id);
    return playlistMapper.toPlaylistDto(playlist);
  }

  @GetMapping("/playlists")
  public List<PlaylistDto> getAllPlaylists() {
    return playlistService.getPlaylists().stream().map(playlistMapper::toPlaylistDto).toList();
  }

  @PostMapping
  public PlaylistDto addPlaylist(@RequestBody @Nonnull final PlaylistDto dto) {
    final var playlistToAdd = playlistMapper.toPlaylist(dto);
    final var playlist = playlistService.addPlaylist(playlistToAdd);
    log.info("POST: Playlist was added");
    return playlistMapper.toPlaylistDto(playlist);
  }

  @PutMapping("/{playlistId}")
  public PlaylistDto modifyPlaylist(@RequestBody @Nonnull final PlaylistDto dto,
                                    @PathVariable("playlistId") final Long playlistId) {
    final var playlist = playlistService.modifyPlaylist(playlistId, dto);
    log.info("PUT: Playlist with id {} was updated", playlistId);
    return playlistMapper.toPlaylistDto(playlist);
  }

  @DeleteMapping("/{playlistId}/remove")
  public ResponseEntity<Object> removeSong(@PathVariable final Long playlistId,
                                           @RequestParam final Long songId) {
    playlistService.removeSong(playlistId, songId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("/{id}")
  public PlaylistDto deletePlaylist(@PathVariable("id") final Long playlistId) {
    final var response = playlistService.deletePlaylist(playlistId);
    log.info("DELETE: Playlist with id {} was deleted", playlistId);
    return playlistMapper.toPlaylistDto(response);
  }
}
