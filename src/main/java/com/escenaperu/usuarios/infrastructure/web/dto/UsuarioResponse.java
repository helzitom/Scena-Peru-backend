package com.escenaperu.usuarios.infrastructure.web.dto;

import com.escenaperu.usuarios.domain.TipoUsuario;
import com.escenaperu.usuarios.domain.Usuario;
import java.util.UUID;

public record UsuarioResponse(
        UUID id, String email, TipoUsuario tipo,
        String nombreDisplay, boolean verificado
) {
    public static UsuarioResponse desde(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getEmail(), u.getTipo(), u.getNombreDisplay(), u.isVerificado());
    }
}
