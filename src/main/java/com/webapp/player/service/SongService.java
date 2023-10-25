package com.webapp.player.service;

import com.webapp.player.dto.SongDto;
import com.webapp.player.persistence.entity.Song;

public interface SongService {
  Song getSongById(final Long id);
  Song addSong(final Song song);
  Song modifySong(final Long songId, final SongDto songDto);
  Song deleteSong(final Long id);
}
