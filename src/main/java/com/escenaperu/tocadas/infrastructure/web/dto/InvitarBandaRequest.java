package com.escenaperu.tocadas.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record InvitarBandaRequest(@NotNull UUID bandaId, Integer ordenAparicion) {}
