package com.escenaperu.notificaciones.infrastructure.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * Canal en tiempo real. El cliente se suscribe a /topic/ciudad/{ciudadId}
 * y recibe cada nueva tocada apenas se crea, sin hacer polling.
 * Este modulo es el candidato natural a salir como microservicio propio
 * si el numero de conexiones concurrentes crece (ver diagrama de arquitectura).
 */
@Configuration
@EnableWebSocketMessageBroker
public class NotificacionWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
