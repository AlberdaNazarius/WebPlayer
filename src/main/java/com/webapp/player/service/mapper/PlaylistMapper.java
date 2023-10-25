package com.webapp.player.service.mapper;

import com.webapp.player.dto.PlaylistDto;
import com.webapp.player.persistence.entity.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
  PlaylistDto toPlaylistDto(Playlist playlist);
  Playlist toPlaylist(PlaylistDto playlistDto);
  void updatePlaylist(@MappingTarget Playlist playlist, PlaylistDto playlistDto);
}
