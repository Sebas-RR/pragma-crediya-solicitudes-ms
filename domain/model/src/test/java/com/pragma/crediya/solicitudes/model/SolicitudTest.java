package com.pragma.crediya.solicitudes.model;

import com.pragma.crediya.solicitudes.model.solicitud.Solicitud;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class SolicitudTest {

    @Test
    void nueva_ok_noLanzaExcepcion() {
        assertThatCode(() ->
                Solicitud.nueva(
                        "123456789",
                        new BigDecimal("5000000"),
                        24,
                        UUID.randomUUID(),
                        Instant.parse("2024-01-01T00:00:00Z")
                )
        ).doesNotThrowAnyException();
    }

    @Test
    void nueva_documentoInvalido_lanzaIAE() {
        assertThatThrownBy(() ->
                Solicitud.nueva("abc", new BigDecimal("100"), 12, UUID.randomUUID(), Instant.now())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void nueva_montoInvalido_lanzaIAE() {
        assertThatThrownBy(() ->
                Solicitud.nueva("123456", new BigDecimal("-1"), 12, UUID.randomUUID(), Instant.now())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void nueva_plazoInvalido_lanzaIAE() {
        assertThatThrownBy(() ->
                Solicitud.nueva("123456", new BigDecimal("100"), 0, UUID.randomUUID(), Instant.now())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void nueva_tipoPrestamoIdNull_lanzaNPE() {
        assertThatThrownBy(() ->
                Solicitud.nueva("123456", new BigDecimal("100"), 12, null, Instant.now())
        ).isInstanceOf(NullPointerException.class);
    }
}
