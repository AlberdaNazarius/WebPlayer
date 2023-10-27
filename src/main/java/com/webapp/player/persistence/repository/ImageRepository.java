package com.webapp.player.persistence.repository;

import com.webapp.player.persistence.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
  Optional<Image> findByName(String name);
}
