package com.webapp.player.persistence.repository;

import com.webapp.player.persistence.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
  List<Playlist> findByUsersUsername(String username);
}
