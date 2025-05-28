package com.webapp.player.persistence.repository;

import com.webapp.player.persistence.entity.Playlist;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
  List<Playlist> findByUsersUsername(String username);
  @EntityGraph(attributePaths = {"users", "songs"})
  @Query("SELECT p FROM Playlist p WHERE p.id = :id")
  Optional<Playlist> findByIdWithUsers(@Param("id") Long id);
}
