package com.escenaperu.shared.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Habilita que los @EventListener marcados con @Async (ej: notificaciones)
 * se ejecuten en un hilo aparte y no bloqueen el caso de uso que publico
 * el evento (ej: crear una tocada no debe esperar a que se genere la notif.).
 */
@Configuration
@EnableAsync
public class AsyncConfig {
}
