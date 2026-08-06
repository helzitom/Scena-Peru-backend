package com.escenaperu.auth.infrastructure.web;

import com.escenaperu.auth.application.*;
import com.escenaperu.auth.infrastructure.web.dto.LoginRequest;
import com.escenaperu.auth.infrastructure.web.dto.RefreshRequest;
import com.escenaperu.auth.infrastructure.web.dto.TokenResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RefrescarTokenUseCase refrescarTokenUseCase;
    private final CerrarSesionUseCase cerrarSesionUseCase;

    public AuthController(LoginUseCase loginUseCase, RefrescarTokenUseCase refrescarTokenUseCase,
                          CerrarSesionUseCase cerrarSesionUseCase) {
        this.loginUseCase = loginUseCase;
        this.refrescarTokenUseCase = refrescarTokenUseCase;
        this.cerrarSesionUseCase = cerrarSesionUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        var resultado = loginUseCase.ejecutar(new LoginCommand(request.email(), request.password()));
        return ResponseEntity.ok(TokenResponse.desde(resultado));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refrescar(@Valid @RequestBody RefreshRequest request) {
        var resultado = refrescarTokenUseCase.ejecutar(request.refreshToken());
        return ResponseEntity.ok(TokenResponse.desde(resultado));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshRequest request) {
        cerrarSesionUseCase.ejecutar(request.refreshToken());
        return ResponseEntity.noContent().build();
    }
}