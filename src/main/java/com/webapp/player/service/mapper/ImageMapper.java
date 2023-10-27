package com.webapp.player.service.mapper;

import com.webapp.player.dto.ImageDto;
import com.webapp.player.persistence.entity.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
  ImageDto toImageDto(Image image);
  Image toImage(ImageDto imageDto);
}
