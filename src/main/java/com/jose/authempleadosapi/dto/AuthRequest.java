package com.jose.authempleadosapi.dto;

import com.jose.authempleadosapi.entity.Role;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Data;

@Data
public class AuthRequest {
  @NotBlank(message = "El nombre de usuario no puede estar vacío")
  private String username;

  @NotBlank(message = "La contraseña no puede estar vacía")
  private String password;

  private List<Role> roles;
}
