package com.escenaperu.bandas.domain;

import java.util.List;
import java.util.UUID;

/**
 * Sigue el mismo patron domain/infrastructure que "usuarios" y "tocadas".
 * El caso de uso RegistrarPerfilBandaUseCase y su controller se agregan
 * exactamente igual que en usuarios/UsuarioController, solo cambia el
 * shape de los datos (genero, redes sociales, integrantes, etc).
 */
public class PerfilBanda {
    private final UUID usuarioId;
    private final String nombreBanda;
    private final String generoPrincipal;
    private final List<String> generosSecundarios;
    private final String biografia;

    public PerfilBanda(UUID usuarioId, String nombreBanda, String generoPrincipal,
                        List<String> generosSecundarios, String biografia) {
        this.usuarioId = usuarioId;
        this.nombreBanda = nombreBanda;
        this.generoPrincipal = generoPrincipal;
        this.generosSecundarios = generosSecundarios;
        this.biografia = biografia;
    }

    public UUID getUsuarioId() { return usuarioId; }
    public String getNombreBanda() { return nombreBanda; }
    public String getGeneroPrincipal() { return generoPrincipal; }
    public List<String> getGenerosSecundarios() { return generosSecundarios; }
    public String getBiografia() { return biografia; }
}
