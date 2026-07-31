package com.escenaperu.shared.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Adaptador que hoy usa el bus de eventos en memoria de Spring.
 * Si se extrae un modulo a microservicio, este es el UNICO archivo
 * que cambia (por un productor de Kafka/RabbitMQ, por ejemplo).
 */
@Component
public class SpringEventPublisherAdapter implements EventPublisherPort {

    private final ApplicationEventPublisher springPublisher;

    public SpringEventPublisherAdapter(ApplicationEventPublisher springPublisher) {
        this.springPublisher = springPublisher;
    }

    @Override
    public void publish(DomainEvent event) {
        springPublisher.publishEvent(event);
    }
}
