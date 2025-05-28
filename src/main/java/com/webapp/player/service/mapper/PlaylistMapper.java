package com.webapp.player.service.mapper;

import com.webapp.player.dto.PlaylistDto;
import com.webapp.player.persistence.entity.Playlist;
import org.mapstruct.*;

@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Mapper(componentModel = "spring")
public interface PlaylistMapper {
  PlaylistDto toPlaylistDto(Playlist playlist);
  @Mapping(target = "users", ignore = true)
  Playlist toPlaylist(PlaylistDto playlistDto);
  void updatePlaylist(@MappingTarget Playlist playlist, PlaylistDto playlistDto);
}
