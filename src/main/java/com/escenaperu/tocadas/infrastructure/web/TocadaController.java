package com.escenaperu.tocadas.infrastructure.web;

import com.escenaperu.tocadas.application.*;
import com.escenaperu.tocadas.domain.TocadaRepository;
import com.escenaperu.tocadas.infrastructure.web.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tocadas")
public class TocadaController {

    private final CrearTocadaUseCase crearTocadaUseCase;
    private final InvitarBandaALineupUseCase invitarBandaUseCase;
    private final TocadaRepository tocadaRepository;

    public TocadaController(CrearTocadaUseCase crearTocadaUseCase,
                             InvitarBandaALineupUseCase invitarBandaUseCase,
                             TocadaRepository tocadaRepository) {
        this.crearTocadaUseCase = crearTocadaUseCase;
        this.invitarBandaUseCase = invitarBandaUseCase;
        this.tocadaRepository = tocadaRepository;
    }

    // Sirve tanto para una banda autogestionando su tocada acustica
    // como para un organizador creando un festival con cartel completo.
    @PostMapping
    public ResponseEntity<TocadaResponse> crear(@Valid @RequestBody CrearTocadaRequest request) {
        var comando = new CrearTocadaCommand(
                request.titulo(), request.descripcion(), request.ciudadId(), request.venueId(),
                request.ubicacionManual(), request.fecha(), request.horaInicio(),
                request.creadorTipo(), request.creadorId(), request.precioEntrada(),
                request.linkEntradas(), request.imagenFlyerUrl()
        );
        var tocada = crearTocadaUseCase.ejecutar(comando);
        return ResponseEntity.status(HttpStatus.CREATED).body(TocadaResponse.desde(tocada));
    }

    @PostMapping("/{tocadaId}/lineup")
    public ResponseEntity<Void> invitarBanda(@PathVariable UUID tocadaId,
                                              @Valid @RequestBody InvitarBandaRequest request) {
        invitarBandaUseCase.ejecutar(new InvitarBandaALineupCommand(
                tocadaId, request.bandaId(), request.ordenAparicion()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Vista de mapa de tocadas por ciudad: GET /api/tocadas?ciudadId=1
    @GetMapping
    public ResponseEntity<List<TocadaResponse>> listarPorCiudad(@RequestParam Integer ciudadId) {
        var tocadas = tocadaRepository.findByCiudadAndFechaDesde(ciudadId, LocalDate.now())
                .stream().map(TocadaResponse::desde).toList();
        return ResponseEntity.ok(tocadas);
    }
}
