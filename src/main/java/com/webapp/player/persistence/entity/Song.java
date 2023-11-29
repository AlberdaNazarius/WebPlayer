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

  @Column(name = "added_date")
  Date addedDate;

  @Column(name = "duration")
  String duration;

  @Column(name = "image")
  String imageKey;

  @ManyToMany()
  @JoinTable(name = "playlist_song",
          joinColumns = @JoinColumn(name = "playlist_id"),
          inverseJoinColumns = @JoinColumn(name = "song_id"))
  @JsonIgnore
  Set<Playlist> playlists;
}
