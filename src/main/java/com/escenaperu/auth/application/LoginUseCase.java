package com.escenaperu.auth.application;

import com.escenaperu.auth.domain.RefreshToken;
import com.escenaperu.auth.domain.RefreshTokenRepository;
import com.escenaperu.shared.security.JwtProperties;
import com.escenaperu.shared.security.JwtService;
import com.escenaperu.usuarios.domain.PasswordHasher;
import com.escenaperu.usuarios.domain.Usuario;
import com.escenaperu.usuarios.domain.UsuarioRepository;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class LoginUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginUseCase(UsuarioRepository usuarioRepository, PasswordHasher passwordHasher, JwtService jwtService,
                        JwtProperties jwtProperties, RefreshTokenRepository refreshTokenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
        this.jwtService = jwtService;
        this.jwtProperties = jwtProperties;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public LoginResult ejecutar(LoginCommand comando) {
        Usuario usuario = usuarioRepository.findByEmail(comando.email())
                .orElseThrow(CredencialesInvalidasException::new); // no revela si el email existe

        if (!passwordHasher.coincide(comando.password(), usuario.getPasswordHash())) {
            throw new CredencialesInvalidasException();
        }

        String accessToken = jwtService.generarAccessToken(usuario.getId(), usuario.getEmail(), usuario.getTipo().name());

        String refreshTokenPlano = generarTokenAleatorio();
        Instant expiraEn = Instant.now().plusSeconds(jwtProperties.getRefreshTokenDays() * 24 * 60 * 60);
        refreshTokenRepository.save(RefreshToken.emitir(usuario.getId(), sha256(refreshTokenPlano), expiraEn));

        return new LoginResult(accessToken, refreshTokenPlano, jwtService.accessTokenExpiraEnSegundos());
    }

    private String generarTokenAleatorio() {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(KeyGenerators.secureRandom(48).generateKey());
    }

    static String sha256(String valor) {
        try {
            var digest = MessageDigest.getInstance("SHA-256").digest(valor.getBytes());
            return HexFormat.of().formatHex(digest);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}