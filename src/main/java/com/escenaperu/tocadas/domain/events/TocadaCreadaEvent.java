package com.escenaperu.tocadas.domain.events;

import com.escenaperu.shared.events.DomainEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Se publica cuando una tocada queda registrada (autogestionada u organizada).
 * El modulo "notificaciones" escucha este evento sin que "tocadas" sepa que existe.
 */
public record TocadaCreadaEvent(
        UUID tocadaId,
        Integer ciudadId,
        String titulo,
        LocalDate fecha,
        Instant occurredOn
) implements DomainEvent {

    public static TocadaCreadaEvent de(UUID tocadaId, Integer ciudadId, String titulo, LocalDate fecha) {
        return new TocadaCreadaEvent(tocadaId, ciudadId, titulo, fecha, Instant.now());
    }

    @Override
    public Instant occurredOn() { return occurredOn; }
}
