package com.escenaperu.auth.infrastructure.persistence;

import com.escenaperu.auth.domain.RefreshToken;
import com.escenaperu.auth.domain.RefreshTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository jpaRepository;

    public RefreshTokenRepositoryAdapter(RefreshTokenJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public RefreshToken save(RefreshToken rt) {
        var entidad = new RefreshTokenJpaEntity(rt.getId(), rt.getUsuarioId(), rt.getTokenHash(),
                rt.getExpiraEn(), rt.isRevocado(), rt.getCreatedAt());
        var guardada = jpaRepository.save(entidad);
        return new RefreshToken(guardada.getId(), guardada.getUsuarioId(), guardada.getTokenHash(),
                guardada.getExpiraEn(), guardada.isRevocado(), guardada.getCreatedAt());
    }

    @Override
    public Optional<RefreshToken> findByTokenHash(String tokenHash) {
        return jpaRepository.findByTokenHash(tokenHash).map(e -> new RefreshToken(
                e.getId(), e.getUsuarioId(), e.getTokenHash(), e.getExpiraEn(), e.isRevocado(), e.getCreatedAt()));
    }

    @Override
    @Transactional
    public void revocarTodosDeUsuario(UUID usuarioId) {
        jpaRepository.revocarTodosDeUsuario(usuarioId);
    }
}