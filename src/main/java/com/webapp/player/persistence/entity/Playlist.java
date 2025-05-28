package com.webapp.player.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Playlist {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  Long id;

  @Column(name = "name")
  String name;

  @Column(name = "image_key")
  String imageKey;

  @ManyToMany()
  @JoinTable(name = "playlist_song",
          joinColumns = @JoinColumn(name = "playlist_id"),
          inverseJoinColumns = @JoinColumn(name = "song_id"))
  Set<Song> songs = new HashSet<>();

  @Builder.Default
  @ManyToMany(mappedBy = "playlists")
  @JsonIgnore
  Set<User> users = new HashSet<>();

  public void addUser(User user) {
    if (this.users == null) {
      this.users = new HashSet<>();
    }
    this.users.add(user);

    if (user.getPlaylists() == null) {
      user.setPlaylists(new HashSet<>());
    }
    user.getPlaylists().add(this);
  }

  public void removeUser(User user) {
    this.users.remove(user);
    user.getPlaylists().remove(this);
  }
}
