package com.jose.authempleadosapi.service;

import com.jose.authempleadosapi.dto.AuthRequest;
import com.jose.authempleadosapi.dto.AuthResponse;
import com.jose.authempleadosapi.entity.Role;
import com.jose.authempleadosapi.entity.User;
import com.jose.authempleadosapi.repository.UserRepository;
import com.jose.authempleadosapi.util.JwtUtil;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthResponse register(AuthRequest request) {
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new IllegalArgumentException("El nombre de usuario ya está en uso");
    }

    List<Role> roles =
        (request.getRoles() != null && !request.getRoles().isEmpty())
            ? request.getRoles()
            : Collections.singletonList(Role.USER); // un rol por defecto

    User user =
        User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .roles(roles)
            .build();

    userRepository.save(user);

    String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
    return new AuthResponse(token);
  }

  public AuthResponse login(AuthRequest request) {
    User user =
        userRepository
            .findByUsername(request.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new BadCredentialsException("Credenciales inválidas");
    }

    String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
    return new AuthResponse(token);
  }
}
