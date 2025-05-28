package com.webapp.player.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Song {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  Long id;

  @Column(name = "name")
  String name;

  @Column(name = "author")
  String author;

  @Column(name = "album")
  String album;

  @Column(name = "added_date")
  Date addedDate;

  @Column(name = "duration")
  String duration;

  @Column(name = "image_key")
  String imageKey;

  @Column(name = "song_key")
  String songKey;

  @JsonIgnore
  @ManyToMany(mappedBy = "songs")
  Set<Playlist> playlists;

  public void removeFromPlaylist(Playlist playlist) {
    this.playlists.remove(playlist);
    playlist.getSongs().remove(this);
  }
}
