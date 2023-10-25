package com.webapp.player.service.mapper;

import com.webapp.player.dto.SongDto;
import com.webapp.player.persistence.entity.Song;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SongMapper {
  SongDto toSongDto(Song song);
  Song toSong(SongDto songDto);
  void updateSong(@MappingTarget Song song, SongDto songDto);
}
