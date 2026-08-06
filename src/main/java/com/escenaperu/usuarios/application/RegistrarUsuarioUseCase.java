package com.escenaperu.usuarios.application;

import com.escenaperu.usuarios.domain.PasswordHasher;
import com.escenaperu.usuarios.domain.Usuario;
import com.escenaperu.usuarios.domain.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistrarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordHasher passwordHasher;

    public RegistrarUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordHasher passwordHasher) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
    }

    public Usuario ejecutar(RegistrarUsuarioCommand comando) {
        if (usuarioRepository.existsByEmail(comando.email())) {
            throw new IllegalStateException("Ya existe una cuenta con ese email");
        }
        String hash = passwordHasher.hash(comando.password()); // nunca se guarda en texto plano
        Usuario nuevo = Usuario.registrar(comando.email(), hash, comando.tipo(),
                comando.ciudadId(), comando.nombreDisplay());
        return usuarioRepository.save(nuevo);
    }
}