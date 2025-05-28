package com.webapp.player.controller;

import com.webapp.player.dto.PlaylistDto;
import com.webapp.player.persistence.entity.Playlist;
import com.webapp.player.persistence.entity.User;
import com.webapp.player.persistence.repository.UserRepository;
import com.webapp.player.service.PlaylistService;
import com.webapp.player.service.mapper.PlaylistMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
@Slf4j
public class PlaylistController {
  private final PlaylistService playlistService;
  private final UserRepository userRepository;
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

  @GetMapping("/playlists/user")
  public List<PlaylistDto> getUserPlaylists() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    List<Playlist> userPlaylists = playlistService.getPlaylistsByUsername(username);
    return userPlaylists.stream().map(playlistMapper::toPlaylistDto).toList();
  }

  @PostMapping
  public PlaylistDto addPlaylist(@RequestBody @Nonnull final PlaylistDto dto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    final Playlist playlistToAdd = playlistMapper.toPlaylist(dto);
    User currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    playlistToAdd.addUser(currentUser);

    final Playlist playlist = playlistService.addPlaylist(playlistToAdd);
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
  public ResponseEntity<?> deletePlaylist(@PathVariable("id") final Long playlistId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    playlistService.deletePlaylist(playlistId, username);
    log.info("DELETE: Playlist with id {} was deleted", playlistId);
    return ResponseEntity.ok().build();
  }
}
