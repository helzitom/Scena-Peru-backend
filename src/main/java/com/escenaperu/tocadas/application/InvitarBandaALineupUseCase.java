package com.escenaperu.tocadas.application;

import com.escenaperu.tocadas.domain.TocadaLineup;
import com.escenaperu.tocadas.domain.TocadaLineupRepository;
import com.escenaperu.tocadas.domain.TocadaRepository;
import org.springframework.stereotype.Service;

/**
 * Un organizador invita a una banda a su cartel. La banda queda en estado
 * PENDIENTE hasta que la acepte (endpoint no incluido aqui por brevedad,
 * pero sigue el mismo patron: un nuevo caso de uso que actualiza el estado).
 */
@Service
public class InvitarBandaALineupUseCase {

    private final TocadaRepository tocadaRepository;
    private final TocadaLineupRepository lineupRepository;

    public InvitarBandaALineupUseCase(TocadaRepository tocadaRepository,
                                       TocadaLineupRepository lineupRepository) {
        this.tocadaRepository = tocadaRepository;
        this.lineupRepository = lineupRepository;
    }

    public TocadaLineup ejecutar(InvitarBandaALineupCommand comando) {
        tocadaRepository.findById(comando.tocadaId())
                .orElseThrow(() -> new IllegalArgumentException("La tocada no existe"));

        TocadaLineup invitacion = TocadaLineup.invitar(
                comando.tocadaId(), comando.bandaId(), comando.ordenAparicion()
        );
        return lineupRepository.save(invitacion);
    }
}
