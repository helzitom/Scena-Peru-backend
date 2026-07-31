package com.escenaperu.usuarios.application;

import com.escenaperu.usuarios.domain.Usuario;
import com.escenaperu.usuarios.domain.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistrarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public RegistrarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario ejecutar(RegistrarUsuarioCommand comando) {
        if (usuarioRepository.existsByEmail(comando.email())) {
            throw new IllegalStateException("Ya existe una cuenta con ese email");
        }
        Usuario nuevo = Usuario.registrar(
                comando.email(), comando.passwordHash(), comando.tipo(),
                comando.ciudadId(), comando.nombreDisplay()
        );
        return usuarioRepository.save(nuevo);
    }
}
