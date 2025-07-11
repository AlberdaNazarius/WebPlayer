package com.webapp.player.service.imp;

import com.webapp.player.common.error.SongNotFoundException;
import com.webapp.player.dto.SongDto;
import com.webapp.player.persistence.entity.Song;
import com.webapp.player.persistence.repository.SongRepository;
import com.webapp.player.service.SongService;
import com.webapp.player.service.mapper.SongMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
  private final SongRepository songRepository;
  private final SongMapper songMapper;


  @Override
  @Transactional(readOnly = true)
  public Song getSongById(Long id) {
    return songRepository.getReferenceById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Song> getSongs() {
    return songRepository.findAll();
  }

  @Override
  @Transactional
  public Song addSong(Song song) {
    return songRepository.save(song);
  }

  @Override
  @Transactional
  public Song modifySong(Long songId, SongDto songDto) {
    final var song = songRepository.findById(songId);
    if (song.isEmpty()) {
      throw new SongNotFoundException("Song with such id was not found");
    }
    songMapper.updateSong(song.get(), songDto);
    return songRepository.save(song.get());
  }

  @Override
  @Transactional
  public Song deleteSong(Long id) {
    final var songToDelete = songRepository.findById(id);
    if (songToDelete.isEmpty()) {
      throw new SongNotFoundException("Song with such id was not found");
    }
    songRepository.delete(songToDelete.get());
    return songToDelete.get();
  }
}
