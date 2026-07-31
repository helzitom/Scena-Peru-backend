package com.escenaperu.tocadas.infrastructure.persistence;

import com.escenaperu.tocadas.domain.CreadorTipo;
import com.escenaperu.tocadas.domain.EstadoTocada;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tocadas")
public class TocadaJpaEntity {

    @Id
    private UUID id;

    private String titulo;
    private String descripcion;

    @Column(name = "ciudad_id")
    private Integer ciudadId;

    @Column(name = "venue_id")
    private Integer venueId;

    @Column(name = "ubicacion_manual")
    private String ubicacionManual;

    private LocalDate fecha;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Enumerated(EnumType.STRING)
    @Column(name = "creador_tipo")
    private CreadorTipo creadorTipo;

    @Column(name = "creador_id")
    private UUID creadorId;

    @Enumerated(EnumType.STRING)
    private EstadoTocada estado;

    @Column(name = "precio_entrada")
    private BigDecimal precioEntrada;

    @Column(name = "link_entradas")
    private String linkEntradas;

    @Column(name = "imagen_flyer_url")
    private String imagenFlyerUrl;

    @Column(name = "created_at")
    private Instant createdAt;

    protected TocadaJpaEntity() { }

    public TocadaJpaEntity(UUID id, String titulo, String descripcion, Integer ciudadId, Integer venueId,
                            String ubicacionManual, LocalDate fecha, LocalTime horaInicio,
                            CreadorTipo creadorTipo, UUID creadorId, EstadoTocada estado,
                            BigDecimal precioEntrada, String linkEntradas, String imagenFlyerUrl,
                            Instant createdAt) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ciudadId = ciudadId;
        this.venueId = venueId;
        this.ubicacionManual = ubicacionManual;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.creadorTipo = creadorTipo;
        this.creadorId = creadorId;
        this.estado = estado;
        this.precioEntrada = precioEntrada;
        this.linkEntradas = linkEntradas;
        this.imagenFlyerUrl = imagenFlyerUrl;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public Integer getCiudadId() { return ciudadId; }
    public Integer getVenueId() { return venueId; }
    public String getUbicacionManual() { return ubicacionManual; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public CreadorTipo getCreadorTipo() { return creadorTipo; }
    public UUID getCreadorId() { return creadorId; }
    public EstadoTocada getEstado() { return estado; }
    public BigDecimal getPrecioEntrada() { return precioEntrada; }
    public String getLinkEntradas() { return linkEntradas; }
    public String getImagenFlyerUrl() { return imagenFlyerUrl; }
    public Instant getCreatedAt() { return createdAt; }
}
