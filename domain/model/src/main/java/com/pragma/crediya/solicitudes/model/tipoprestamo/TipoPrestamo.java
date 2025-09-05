package com.pragma.crediya.solicitudes.model.tipoprestamo;

import java.math.BigDecimal;
import java.util.UUID;

public record TipoPrestamo(
        UUID id,
        String nombre,
        BigDecimal montoMinimo,
        BigDecimal montoMaximo,
        double tasaInteres,
        boolean validacionAutomatica) { }
