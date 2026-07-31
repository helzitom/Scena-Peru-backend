package com.escenaperu.tocadas.application;

import com.escenaperu.shared.events.EventPublisherPort;
import com.escenaperu.tocadas.domain.Tocada;
import com.escenaperu.tocadas.domain.TocadaRepository;
import com.escenaperu.tocadas.domain.events.TocadaCreadaEvent;
import org.springframework.stereotype.Service;

/**
 * Cubre AMBOS casos de uso reales de la escena:
 *  - una banda que se autogestiona su propia tocada (creadorTipo = BANDA)
 *  - un organizador/promotor/venue que arma el evento (creadorTipo = ORGANIZADOR)
 * La diferencia vive solo en el dato "creadorTipo/creadorId"; el flujo es identico.
 */
@Service
public class CrearTocadaUseCase {

    private final TocadaRepository tocadaRepository;
    private final EventPublisherPort eventPublisher;

    public CrearTocadaUseCase(TocadaRepository tocadaRepository, EventPublisherPort eventPublisher) {
        this.tocadaRepository = tocadaRepository;
        this.eventPublisher = eventPublisher;
    }

    public Tocada ejecutar(CrearTocadaCommand comando) {
        Tocada tocada = Tocada.crear(
                comando.titulo(), comando.descripcion(), comando.ciudadId(), comando.venueId(),
                comando.ubicacionManual(), comando.fecha(), comando.horaInicio(),
                comando.creadorTipo(), comando.creadorId(), comando.precioEntrada(),
                comando.linkEntradas(), comando.imagenFlyerUrl()
        );

        Tocada guardada = tocadaRepository.save(tocada);

        eventPublisher.publish(TocadaCreadaEvent.de(
                guardada.getId(), guardada.getCiudadId(), guardada.getTitulo(), guardada.getFecha()
        ));

        return guardada;
    }
}
