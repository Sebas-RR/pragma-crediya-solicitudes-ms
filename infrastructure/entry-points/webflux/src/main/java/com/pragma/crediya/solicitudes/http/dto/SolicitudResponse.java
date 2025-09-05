package com.pragma.crediya.solicitudes.http.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record SolicitudResponse(
        UUID id,
        String estado,
        String documento,
        BigDecimal monto,
        Integer plazoMeses,
        UUID tipoPrestamoId,
        Instant createdAt
) {}
