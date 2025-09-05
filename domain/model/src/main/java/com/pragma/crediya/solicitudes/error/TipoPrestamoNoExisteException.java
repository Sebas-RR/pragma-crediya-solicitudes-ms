package com.pragma.crediya.solicitudes.error;

import java.util.UUID;

public class TipoPrestamoNoExisteException extends RuntimeException {
    public TipoPrestamoNoExisteException(UUID id) {
        super("Tipo de préstamo no existe: " + id);
    }
}
