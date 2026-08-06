package com.escenaperu.usuarios.infrastructure.web;

import com.escenaperu.usuarios.application.RegistrarUsuarioCommand;
import com.escenaperu.usuarios.application.RegistrarUsuarioUseCase;
import com.escenaperu.usuarios.infrastructure.web.dto.RegistrarUsuarioRequest;
import com.escenaperu.usuarios.infrastructure.web.dto.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.escenaperu.usuarios.domain.UsuarioRepository;
import com.escenaperu.usuarios.infrastructure.web.dto.UsuarioResponse;
import org.springframework.security.core.Authentication;
import java.util.UUID;

// Nota: el hash real de password (BCrypt) y la emision de JWT se agregan
// al integrar Spring Security; aqui se deja el punto de entrada del caso de uso.
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(RegistrarUsuarioUseCase registrarUsuarioUseCase, UsuarioRepository usuarioRepository) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody RegistrarUsuarioRequest request) {
        var comando = new RegistrarUsuarioCommand(
                request.email(), request.password(), request.tipo(),
                request.ciudadId(), request.nombreDisplay()
        );
        var usuario = registrarUsuarioUseCase.ejecutar(comando);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponse.desde(usuario));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> obtenerPerfil(Authentication authentication) {
        UUID usuarioId = UUID.fromString((String) authentication.getPrincipal());
        return usuarioRepository.findById(usuarioId)
                .map(UsuarioResponse::desde)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
