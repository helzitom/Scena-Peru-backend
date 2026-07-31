package com.escenaperu.notificaciones.infrastructure.persistence;

import com.escenaperu.notificaciones.domain.TipoNotificacion;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notificaciones")
public class NotificacionJpaEntity {

    @Id
    private UUID id;

    @Column(name = "ciudad_id")
    private Integer ciudadId;

    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipo;

    @Column(name = "referencia_id")
    private UUID referenciaId;

    private String contenido;
    private boolean leido;

    @Column(name = "created_at")
    private Instant createdAt;

    protected NotificacionJpaEntity() { }

    public NotificacionJpaEntity(UUID id, Integer ciudadId, TipoNotificacion tipo, UUID referenciaId,
                                  String contenido, boolean leido, Instant createdAt) {
        this.id = id;
        this.ciudadId = ciudadId;
        this.tipo = tipo;
        this.referenciaId = referenciaId;
        this.contenido = contenido;
        this.leido = leido;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public Integer getCiudadId() { return ciudadId; }
    public TipoNotificacion getTipo() { return tipo; }
    public UUID getReferenciaId() { return referenciaId; }
    public String getContenido() { return contenido; }
    public boolean isLeido() { return leido; }
    public Instant getCreatedAt() { return createdAt; }
}
