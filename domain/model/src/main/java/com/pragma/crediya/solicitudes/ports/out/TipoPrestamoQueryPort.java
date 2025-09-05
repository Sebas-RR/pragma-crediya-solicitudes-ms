package com.pragma.crediya.solicitudes.ports.out;

import com.pragma.crediya.solicitudes.model.tipoprestamo.TipoPrestamo;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TipoPrestamoQueryPort {
    Mono<TipoPrestamo> findById(UUID id);
}
