package com.webapp.player.service;

import com.webapp.player.dto.PlaylistDto;
import com.webapp.player.persistence.entity.Playlist;

public interface PlaylistService {
  Playlist getPlaylistById(final Long id);
  Playlist addPlaylist(final Playlist playlist);
  Playlist modifyPlaylist(final Long playlistId, final PlaylistDto playlistDto);
  Playlist deletePlaylist(final Long id);
}