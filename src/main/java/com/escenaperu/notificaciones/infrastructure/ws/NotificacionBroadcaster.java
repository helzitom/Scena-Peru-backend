package com.escenaperu.notificaciones.infrastructure.ws;

import com.escenaperu.notificaciones.domain.Notificacion;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificacionBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacionBroadcaster(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void emitirACiudad(Integer ciudadId, Notificacion notificacion) {
        messagingTemplate.convertAndSend("/topic/ciudad/" + ciudadId, notificacion.getContenido());
    }
}
