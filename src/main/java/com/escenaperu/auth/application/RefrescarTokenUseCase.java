package com.escenaperu.auth.application;

import com.escenaperu.auth.domain.RefreshToken;
import com.escenaperu.auth.domain.RefreshTokenRepository;
import com.escenaperu.shared.security.JwtProperties;
import com.escenaperu.shared.security.JwtService;
import com.escenaperu.usuarios.domain.Usuario;
import com.escenaperu.usuarios.domain.UsuarioRepository;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;

@Service
public class RefrescarTokenUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    public RefrescarTokenUseCase(RefreshTokenRepository refreshTokenRepository, UsuarioRepository usuarioRepository,
                                 JwtService jwtService, JwtProperties jwtProperties) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.jwtProperties = jwtProperties;
    }

    public LoginResult ejecutar(String refreshTokenPlano) {
        String hash = LoginUseCase.sha256(refreshTokenPlano);
        RefreshToken existente = refreshTokenRepository.findByTokenHash(hash)
                .filter(RefreshToken::esValido)
                .orElseThrow(TokenInvalidoException::new);

        Usuario usuario = usuarioRepository.findById(existente.getUsuarioId())
                .orElseThrow(TokenInvalidoException::new);

        // rotacion: revoca toda la familia de tokens del usuario y emite un
        // par nuevo - si el token robado se reutiliza despues, ya no sirve
        refreshTokenRepository.revocarTodosDeUsuario(usuario.getId());

        String accessToken = jwtService.generarAccessToken(usuario.getId(), usuario.getEmail(), usuario.getTipo().name());
        String nuevoRefreshPlano = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(KeyGenerators.secureRandom(48).generateKey());
        Instant expiraEn = Instant.now().plusSeconds(jwtProperties.getRefreshTokenDays() * 24 * 60 * 60);
        refreshTokenRepository.save(RefreshToken.emitir(usuario.getId(), LoginUseCase.sha256(nuevoRefreshPlano), expiraEn));

        return new LoginResult(accessToken, nuevoRefreshPlano, jwtService.accessTokenExpiraEnSegundos());
    }
}