package com.jose.authempleadosapi.controller;

import com.jose.authempleadosapi.dto.AuthRequest;
import com.jose.authempleadosapi.dto.AuthResponse;
import com.jose.authempleadosapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
    AuthResponse response = authService.register(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
    AuthResponse response = authService.login(request);
    return ResponseEntity.ok(response);
  }
}
