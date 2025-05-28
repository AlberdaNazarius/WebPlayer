package com.webapp.player.service.imp;

import com.webapp.player.common.error.PlaylistNotFoundException;
import com.webapp.player.common.error.SongNotFoundException;
import com.webapp.player.dto.PlaylistDto;
import com.webapp.player.persistence.entity.Playlist;
import com.webapp.player.persistence.entity.Song;
import com.webapp.player.persistence.entity.User;
import com.webapp.player.persistence.repository.PlaylistRepository;
import com.webapp.player.persistence.repository.SongRepository;
import com.webapp.player.service.PlaylistService;
import com.webapp.player.service.mapper.PlaylistMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaylistServiceImpl implements PlaylistService {
  private final PlaylistRepository playlistRepository;
  private final SongRepository songRepository;
  private final PlaylistMapper playlistMapper;

  @Override
  @Transactional(readOnly = true)
  public Playlist getPlaylistById(@Nonnull final Long id) {
    final var playlist = playlistRepository.findById(id);
    if (playlist.isEmpty()) {
      throw new PlaylistNotFoundException("Playlist with such id was not found");
    }
    log.info("Playlist with id {} was received", id);
    return playlist.get();
  }

  @Override
  @Transactional(readOnly = true)
  public List<Playlist> getPlaylists() {
    return playlistRepository.findAll();
  }

  @Override
  @Transactional
  public Playlist addPlaylist(@Nonnull final Playlist playlist) {
    if (playlist.getSongs() == null) {
      playlist.setSongs(new HashSet<>());
    }

    log.info("POST: Playlist was added");
    return playlistRepository.save(playlist);
  }

  @Override
  @Transactional
  public Playlist modifyPlaylist(@Nonnull final Long playlistId, @Nonnull final PlaylistDto playlistDto) {
    final var playlist = playlistRepository.findById(playlistId);
    if (playlist.isEmpty()) {
      throw new PlaylistNotFoundException("Playlist with such id was not found");
    }
    playlistMapper.updatePlaylist(playlist.get(), playlistDto);
    return playlistRepository.save(playlist.get());
  }

  @Override
  @Transactional
  public void addSongToPlaylist(Long playlistId, Long songId, String username) {
    Playlist playlist = playlistRepository.findWithUsersAndSongsById(playlistId)
            .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found"));

    boolean isUserAssociated = playlist.getUsers().stream()
            .anyMatch(user -> user.getUsername().equals(username));

    if (!isUserAssociated) {
      log.error("User: {} don't have permission to modify this playlist", username);
    }

    Song song = songRepository.findById(songId)
            .orElseThrow(() -> new SongNotFoundException("Song not found"));

    playlist.addSong(song);

    playlistRepository.save(playlist);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Playlist> getPlaylistsByUsername(String username) {
    return playlistRepository.findByUsersUsername(username);
  }

  @Override
  @Transactional
  public void removeSong(@Nonnull final Long playlistId,
                         @Nonnull final Long songId) {
    final var playlist = playlistRepository.findById(playlistId)
            .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found"));
    final var song = playlist.getSongs().stream()
            .filter(s -> s.getId().equals(songId))
            .findFirst()
            .orElseThrow(() -> new SongNotFoundException("Song not found in playlist"));

    playlist.getSongs().remove(song);
    log.info("Song {} was successfully removed", song.getName());
    playlistRepository.save(playlist);
  }

  @Override
  @Transactional
  public void deletePlaylist(@Nonnull final Long playlistId, final String username) {
    Playlist playlist = playlistRepository.findWithUsersAndSongsById(playlistId)
            .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found"));

    boolean isUserAssociated = playlist.getUsers().stream()
            .anyMatch(user -> user.getUsername().equals(username));

    if (!isUserAssociated) {
      log.info("Received error when deleting playlist");
      return;
    }

    for (User user : new HashSet<>(playlist.getUsers())) {
      user.getPlaylists().remove(playlist);
      playlist.getUsers().remove(user);
    }

    for (Song song : new HashSet<>(playlist.getSongs())) {
      song.getPlaylists().remove(playlist);
      playlist.getSongs().remove(song);
    }

    playlistRepository.delete(playlist);
  }
}
