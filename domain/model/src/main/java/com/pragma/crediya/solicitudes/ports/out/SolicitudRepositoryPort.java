package com.pragma.crediya.solicitudes.ports.out;

import com.pragma.crediya.solicitudes.model.solicitud.Solicitud;
import reactor.core.publisher.Mono;

public interface SolicitudRepositoryPort {
    Mono<Solicitud> save(Solicitud solicitud);
}
