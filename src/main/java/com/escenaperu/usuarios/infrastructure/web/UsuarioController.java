package com.escenaperu.usuarios.infrastructure.web;

import com.escenaperu.usuarios.application.RegistrarUsuarioCommand;
import com.escenaperu.usuarios.application.RegistrarUsuarioUseCase;
import com.escenaperu.usuarios.infrastructure.web.dto.RegistrarUsuarioRequest;
import com.escenaperu.usuarios.infrastructure.web.dto.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Nota: el hash real de password (BCrypt) y la emision de JWT se agregan
// al integrar Spring Security; aqui se deja el punto de entrada del caso de uso.
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;

    public UsuarioController(RegistrarUsuarioUseCase registrarUsuarioUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
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
}
