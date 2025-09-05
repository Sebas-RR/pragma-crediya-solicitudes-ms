package com.pragma.crediya.solicitudes.model.solicitud;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class Solicitud {
    private final UUID id;
    private final String documento;
    private final BigDecimal monto;
    private final int plazoMeses;
    private final UUID tipoPrestamoId;
    private final EstadoSolicitud estado;
    private final Instant createdAt;

    private Solicitud(UUID id, String documento, BigDecimal monto, int plazoMeses,
                      UUID tipoPrestamoId, EstadoSolicitud estado, Instant createdAt) {
        this.id = id; this.documento = documento; this.monto = monto;
        this.plazoMeses = plazoMeses; this.tipoPrestamoId = tipoPrestamoId;
        this.estado = estado; this.createdAt = createdAt;
    }

    public static Solicitud nueva(String documento, BigDecimal monto, int plazoMeses,
                                  UUID tipoPrestamoId, Instant now) {
        validateDocumento(documento);
        validateMonto(monto);
        validatePlazo(plazoMeses);
        Objects.requireNonNull(tipoPrestamoId, "tipoPrestamoId es requerido");

        return new Solicitud(
                UUID.randomUUID(),
                documento,
                monto.stripTrailingZeros(),
                plazoMeses,
                tipoPrestamoId,
                EstadoSolicitud.PENDIENTE_REVISION,
                Objects.requireNonNull(now, "createdAt requerido")
        );
    }

    private static void validateDocumento(String doc) {
        if (doc == null || !doc.matches("\\d{6,20}"))
            throw new IllegalArgumentException("documento inválido");
    }
    private static void validateMonto(BigDecimal m) {
        if (m == null || m.signum() <= 0 || m.scale() > 2)
            throw new IllegalArgumentException("monto inválido");
    }
    private static void validatePlazo(int p) {
        if (p <= 0 || p > 120) throw new IllegalArgumentException("plazoMeses inválido");
    }

    public UUID getId() { return id; }
    public String getDocumento() { return documento; }
    public BigDecimal getMonto() { return monto; }
    public int getPlazoMeses() { return plazoMeses; }
    public UUID getTipoPrestamoId() { return tipoPrestamoId; }
    public EstadoSolicitud getEstado() { return estado; }
    public Instant getCreatedAt() { return createdAt; }
}
