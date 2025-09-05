package com.pragma.crediya.solicitudes.error;

import java.math.BigDecimal;

public class MontoFueraDeRangoException extends RuntimeException {
    public MontoFueraDeRangoException(BigDecimal monto) {
        super("Monto fuera de rango: " + monto);
    }
}
