package com.pragma.crediya.solicitudes.persistence.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Table("solicitud")
public record SolicitudData(
        @Id UUID id_solicitud,
        String documento,
        BigDecimal monto,
        Integer plazo_meses,
        UUID id_tipo_prestamo,
        String estado,
        Instant created_at
) {}