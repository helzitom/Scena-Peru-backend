package com.escenaperu.tocadas.infrastructure.web.dto;

import com.escenaperu.tocadas.domain.CreadorTipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record CrearTocadaRequest(
        @NotBlank String titulo,
        String descripcion,
        @NotNull Integer ciudadId,
        Integer venueId,
        String ubicacionManual,
        @NotNull LocalDate fecha,
        @NotNull LocalTime horaInicio,
        @NotNull CreadorTipo creadorTipo,
        @NotNull UUID creadorId,
        BigDecimal precioEntrada,
        String linkEntradas,
        String imagenFlyerUrl
) {}
