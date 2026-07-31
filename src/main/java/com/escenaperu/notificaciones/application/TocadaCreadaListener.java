package com.escenaperu.notificaciones.application;

import com.escenaperu.notificaciones.domain.Notificacion;
import com.escenaperu.notificaciones.domain.NotificacionRepository;
import com.escenaperu.notificaciones.infrastructure.ws.NotificacionBroadcaster;
import com.escenaperu.tocadas.domain.events.TocadaCreadaEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Este es el UNICO punto donde "notificaciones" conoce algo de "tocadas":
 * el contrato del evento (record inmutable), nunca su logica interna ni su
 * repositorio. Se ejecuta en un hilo aparte (@Async) para no bloquear la
 * creacion de la tocada mientras se genera la notificacion.
 */
@Component
public class TocadaCreadaListener {

    private final NotificacionRepository notificacionRepository;
    private final NotificacionBroadcaster broadcaster;

    public TocadaCreadaListener(NotificacionRepository notificacionRepository,
                                 NotificacionBroadcaster broadcaster) {
        this.notificacionRepository = notificacionRepository;
        this.broadcaster = broadcaster;
    }

    @Async
    @EventListener
    public void alCrearseUnaTocada(TocadaCreadaEvent evento) {
        Notificacion notificacion = Notificacion.nuevaTocada(
                evento.ciudadId(), evento.tocadaId(), evento.titulo()
        );
        Notificacion guardada = notificacionRepository.save(notificacion);
        broadcaster.emitirACiudad(evento.ciudadId(), guardada);
    }
}
