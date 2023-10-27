package com.webapp.player.service.imp;

import com.webapp.player.common.error.PlaylistNotFoundException;
import com.webapp.player.dto.PlaylistDto;
import com.webapp.player.persistence.entity.Playlist;
import com.webapp.player.persistence.repository.ImageRepository;
import com.webapp.player.persistence.repository.PlaylistRepository;
import com.webapp.player.service.PlaylistService;
import com.webapp.player.service.mapper.PlaylistMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
  private final PlaylistRepository playlistRepository;
  private final ImageRepository imageRepository;
  private final PlaylistMapper playlistMapper;

  @Override
  public Playlist getPlaylistById(@Nonnull final Long id) {
    return playlistRepository.getReferenceById(id);
  }

  @Override
  public Playlist addPlaylist(@Nonnull final Playlist playlist, @Nonnull final String imageName) {
    final var playlistImage = imageRepository.findByName(imageName);
    playlist.setImage(playlistImage.get());
    return playlistRepository.save(playlist);
  }

  @Override
  public Playlist modifyPlaylist(@Nonnull final Long playlistId, @Nonnull final PlaylistDto playlistDto) {
    final var playlist = playlistRepository.findById(playlistId);
    if (playlist.isEmpty()) {
      throw new PlaylistNotFoundException("Playlist with such id was not found");
    }
    playlistMapper.updatePlaylist(playlist.get(), playlistDto);
    return playlistRepository.save(playlist.get());
  }

  @Override
  public Playlist deletePlaylist(@Nonnull final Long id) {
    final var playlistToDelete = playlistRepository.findById(id);
    if (playlistToDelete.isEmpty()) {
      throw new PlaylistNotFoundException("Playlist with such id was not found");
    }
    playlistRepository.delete(playlistToDelete.get());
    return playlistToDelete.get();
  }
}
