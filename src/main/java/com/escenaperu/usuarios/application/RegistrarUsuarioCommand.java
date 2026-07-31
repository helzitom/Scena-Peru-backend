package com.escenaperu.usuarios.application;

import com.escenaperu.usuarios.domain.TipoUsuario;

public record RegistrarUsuarioCommand(
        String email,
        String passwordHash,
        TipoUsuario tipo,
        Integer ciudadId,
        String nombreDisplay
) {}
