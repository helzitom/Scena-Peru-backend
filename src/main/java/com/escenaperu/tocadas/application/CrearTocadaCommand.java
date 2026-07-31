package com.escenaperu.tocadas.application;

import com.escenaperu.tocadas.domain.CreadorTipo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record CrearTocadaCommand(
        String titulo,
        String descripcion,
        Integer ciudadId,
        Integer venueId,
        String ubicacionManual,
        LocalDate fecha,
        LocalTime horaInicio,
        CreadorTipo creadorTipo,
        UUID creadorId,
        BigDecimal precioEntrada,
        String linkEntradas,
        String imagenFlyerUrl
) {}
