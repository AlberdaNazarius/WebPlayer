package com.webapp.player.service;

import com.webapp.player.dto.SongDto;
import com.webapp.player.persistence.entity.Song;

import java.util.List;

public interface SongService {
  Song getSongById(final Long id);
  List<Song> getSongs();
  Song addSong(final Song song);
  Song modifySong(final Long songId, final SongDto songDto);
  Song deleteSong(final Long id);
}
