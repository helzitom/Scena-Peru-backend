package com.escenaperu.tocadas.domain;

import java.util.UUID;

public class TocadaLineup {

    private final Long id;
    private final UUID tocadaId;
    private final UUID bandaId;
    private final Integer ordenAparicion;
    private final EstadoInvitacion estadoInvitacion;

    public TocadaLineup(Long id, UUID tocadaId, UUID bandaId, Integer ordenAparicion,
                         EstadoInvitacion estadoInvitacion) {
        this.id = id;
        this.tocadaId = tocadaId;
        this.bandaId = bandaId;
        this.ordenAparicion = ordenAparicion;
        this.estadoInvitacion = estadoInvitacion;
    }

    public static TocadaLineup invitar(UUID tocadaId, UUID bandaId, Integer ordenAparicion) {
        return new TocadaLineup(null, tocadaId, bandaId, ordenAparicion, EstadoInvitacion.PENDIENTE);
    }

    public Long getId() { return id; }
    public UUID getTocadaId() { return tocadaId; }
    public UUID getBandaId() { return bandaId; }
    public Integer getOrdenAparicion() { return ordenAparicion; }
    public EstadoInvitacion getEstadoInvitacion() { return estadoInvitacion; }
}
