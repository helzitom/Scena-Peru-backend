package com.escenaperu.shared.events;

/** Puerto de salida para publicar eventos de dominio. */
public interface EventPublisherPort {
    void publish(DomainEvent event);
}
