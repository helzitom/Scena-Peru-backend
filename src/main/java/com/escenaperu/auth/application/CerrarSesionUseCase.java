package com.escenaperu.auth.application;

import com.escenaperu.auth.domain.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class CerrarSesionUseCase {

    private final RefreshTokenRepository refreshTokenRepository;

    public CerrarSesionUseCase(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void ejecutar(String refreshTokenPlano) {
        String hash = LoginUseCase.sha256(refreshTokenPlano);
        refreshTokenRepository.findByTokenHash(hash)
                .ifPresent(rt -> refreshTokenRepository.revocarTodosDeUsuario(rt.getUsuarioId()));
    }
}