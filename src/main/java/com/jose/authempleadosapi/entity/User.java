package com.jose.authempleadosapi.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private List<Role> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
        .collect(Collectors.toList());
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  // public User() {}

  // public Long getId() { return id; }
  // public void setId(Long id) { this.id = id; }

  // public String getUsername() { return username; }
  // public void setUsername(String username) { this.username = username; }

  // public String getPassword() { return password; }
  // public void setPassword(String password) { this.password = password; }

  // public Role getRole() { return role; }
  // public void setRole(Role role) { this.role = role; }

}
