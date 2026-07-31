package com.escenaperu.usuarios.infrastructure.persistence;

import com.escenaperu.usuarios.domain.Usuario;
import com.escenaperu.usuarios.domain.UsuarioRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

/** Implementa el puerto de dominio usando Spring Data JPA. */
@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final UsuarioJpaRepository jpaRepository;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        var guardado = jpaRepository.save(UsuarioMapper.toEntity(usuario));
        return UsuarioMapper.toDomain(guardado);
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return jpaRepository.findById(id).map(UsuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(UsuarioMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
