package com.escenaperu.usuarios.infrastructure.web.dto;

import com.escenaperu.usuarios.domain.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrarUsuarioRequest(
        @Email @NotBlank String email,
        @NotBlank String password,
        @NotNull TipoUsuario tipo,
        Integer ciudadId,
        @NotBlank String nombreDisplay
) {}
