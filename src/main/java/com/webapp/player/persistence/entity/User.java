package com.webapp.player.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  Long id;

  @Column(name = "username", nullable = false)
  String username;

  @Column(name = "password")
  String password;

  @Column(name = "email")
  String email;

  @Column(name = "image_key")
  String imageKey;

  @ManyToMany(fetch = FetchType.EAGER)
  Collection<Role> roles = new ArrayList<>();

  @Builder.Default
  @ManyToMany()
  @JoinTable(name = "user_playlist",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "playlist_id"))
  Set<Playlist> playlists = new HashSet<>();

  public void addPlaylist(Playlist playlist) {
    if (this.playlists == null) {
      this.playlists = new HashSet<>();
    }
    this.playlists.add(playlist);
    playlist.getUsers().add(this);
  }

  public void removePlaylist(Playlist playlist) {
    this.playlists.remove(playlist);
    playlist.getUsers().remove(this);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
