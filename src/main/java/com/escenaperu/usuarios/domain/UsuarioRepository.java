package com.escenaperu.usuarios.domain;

import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida. La capa application solo conoce esta interfaz,
 * nunca la implementacion JPA de infrastructure.
 */
public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(UUID id);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
