package com.webapp.player.service.imp;

import com.webapp.player.common.error.PlaylistNotFoundException;
import com.webapp.player.common.error.SongNotFoundException;
import com.webapp.player.dto.PlaylistDto;
import com.webapp.player.persistence.entity.Playlist;
import com.webapp.player.persistence.entity.Song;
import com.webapp.player.persistence.repository.PlaylistRepository;
import com.webapp.player.service.PlaylistService;
import com.webapp.player.service.mapper.PlaylistMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaylistServiceImpl implements PlaylistService {
  private final PlaylistRepository playlistRepository;
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
  public Playlist deletePlaylist(@Nonnull final Long id) {
    final var playlistToDelete = playlistRepository.findById(id);
    if (playlistToDelete.isEmpty()) {
      throw new PlaylistNotFoundException("Playlist with such id was not found");
    }
    playlistRepository.delete(playlistToDelete.get());
    log.info("Playlist with id {} was deleted", id);
    return playlistToDelete.get();
  }
}
