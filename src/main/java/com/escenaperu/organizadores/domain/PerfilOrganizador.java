package com.escenaperu.organizadores.domain;

import java.util.UUID;

public class PerfilOrganizador {
    public enum Tipo { PROMOTOR, VENUE, COLECTIVO }

    private final UUID usuarioId;
    private final String nombrePublico;
    private final Tipo tipo;

    public PerfilOrganizador(UUID usuarioId, String nombrePublico, Tipo tipo) {
        this.usuarioId = usuarioId;
        this.nombrePublico = nombrePublico;
        this.tipo = tipo;
    }

    public UUID getUsuarioId() { return usuarioId; }
    public String getNombrePublico() { return nombrePublico; }
    public Tipo getTipo() { return tipo; }
}
