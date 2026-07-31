package com.escenaperu.tocadas.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Tocada {

    private final UUID id;
    private final String titulo;
    private final String descripcion;
    private final Integer ciudadId;
    private final Integer venueId;
    private final String ubicacionManual;
    private final LocalDate fecha;
    private final LocalTime horaInicio;
    private final CreadorTipo creadorTipo;
    private final UUID creadorId;
    private final EstadoTocada estado;
    private final BigDecimal precioEntrada;
    private final String linkEntradas;
    private final String imagenFlyerUrl;
    private final Instant createdAt;

    public Tocada(UUID id, String titulo, String descripcion, Integer ciudadId, Integer venueId,
                  String ubicacionManual, LocalDate fecha, LocalTime horaInicio,
                  CreadorTipo creadorTipo, UUID creadorId, EstadoTocada estado,
                  BigDecimal precioEntrada, String linkEntradas, String imagenFlyerUrl,
                  Instant createdAt) {
        if (venueId == null && (ubicacionManual == null || ubicacionManual.isBlank())) {
            throw new IllegalArgumentException("Debe indicarse un venue o una ubicacion manual (espacio libre)");
        }
        if (fecha == null || fecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de la tocada no puede ser en el pasado");
        }
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

    /** Factory: toda tocada nueva nace CONFIRMADA salvo que se indique lo contrario en application. */
    public static Tocada crear(String titulo, String descripcion, Integer ciudadId, Integer venueId,
                                String ubicacionManual, LocalDate fecha, LocalTime horaInicio,
                                CreadorTipo creadorTipo, UUID creadorId, BigDecimal precioEntrada,
                                String linkEntradas, String imagenFlyerUrl) {
        return new Tocada(UUID.randomUUID(), titulo, descripcion, ciudadId, venueId, ubicacionManual,
                fecha, horaInicio, creadorTipo, creadorId, EstadoTocada.CONFIRMADA,
                precioEntrada, linkEntradas, imagenFlyerUrl, Instant.now());
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
