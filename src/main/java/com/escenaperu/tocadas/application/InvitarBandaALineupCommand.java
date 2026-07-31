package com.escenaperu.tocadas.application;

import java.util.UUID;

public record InvitarBandaALineupCommand(UUID tocadaId, UUID bandaId, Integer ordenAparicion) {}
