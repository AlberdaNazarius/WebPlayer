package com.webapp.player.service;

import com.webapp.player.dto.PlaylistDto;
import com.webapp.player.persistence.entity.Playlist;

import java.util.List;

public interface PlaylistService {
  Playlist getPlaylistById(final Long id);
  List<Playlist> getPlaylists();
  Playlist addPlaylist(final Playlist playlist, final String imageName);
  Playlist modifyPlaylist(final Long playlistId, final PlaylistDto playlistDto);
  Playlist deletePlaylist(final Long id);
}