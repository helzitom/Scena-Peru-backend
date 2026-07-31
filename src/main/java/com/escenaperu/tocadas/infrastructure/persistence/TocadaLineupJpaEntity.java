package com.escenaperu.tocadas.infrastructure.persistence;

import com.escenaperu.tocadas.domain.EstadoInvitacion;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tocada_lineup")
public class TocadaLineupJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tocada_id", nullable = false)
    private UUID tocadaId;

    @Column(name = "banda_id", nullable = false)
    private UUID bandaId;

    @Column(name = "orden_aparicion")
    private Integer ordenAparicion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_invitacion")
    private EstadoInvitacion estadoInvitacion;

    protected TocadaLineupJpaEntity() { }

    public TocadaLineupJpaEntity(Long id, UUID tocadaId, UUID bandaId, Integer ordenAparicion,
                                  EstadoInvitacion estadoInvitacion) {
        this.id = id;
        this.tocadaId = tocadaId;
        this.bandaId = bandaId;
        this.ordenAparicion = ordenAparicion;
        this.estadoInvitacion = estadoInvitacion;
    }

    public Long getId() { return id; }
    public UUID getTocadaId() { return tocadaId; }
    public UUID getBandaId() { return bandaId; }
    public Integer getOrdenAparicion() { return ordenAparicion; }
    public EstadoInvitacion getEstadoInvitacion() { return estadoInvitacion; }
}
