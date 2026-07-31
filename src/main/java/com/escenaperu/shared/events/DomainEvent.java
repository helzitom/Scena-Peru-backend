package com.escenaperu.shared.events;

import java.time.Instant;

/**
 * Contrato base para todo evento de dominio que un modulo publique.
 * Los modulos NUNCA se llaman entre si directamente (ningun modulo importa
 * clases de infrastructure de otro modulo): se comunican publicando eventos
 * como este a traves de EventPublisherPort. Esto es lo que permite que, por
 * ejemplo, "notificaciones" salga como microservicio propio el dia de manana
 * sin tocar el modulo que emite el evento (solo cambia el transporte: de
 * ApplicationEventPublisher en memoria a Kafka/RabbitMQ).
 */
public interface DomainEvent {
    Instant occurredOn();
}
