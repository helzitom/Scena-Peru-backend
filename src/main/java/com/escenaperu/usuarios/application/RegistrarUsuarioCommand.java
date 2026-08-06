package com.escenaperu.usuarios.application;

import com.escenaperu.usuarios.domain.TipoUsuario;

public record RegistrarUsuarioCommand(
        String email,
        String password,
        TipoUsuario tipo,
        Integer ciudadId,
        String nombreDisplay
) {}