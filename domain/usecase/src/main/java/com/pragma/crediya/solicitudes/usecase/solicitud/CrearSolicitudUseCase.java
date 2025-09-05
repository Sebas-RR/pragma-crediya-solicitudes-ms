package com.pragma.crediya.solicitudes.usecase.solicitud;

import com.pragma.crediya.solicitudes.error.MontoFueraDeRangoException;
import com.pragma.crediya.solicitudes.error.TipoPrestamoNoExisteException;
import com.pragma.crediya.solicitudes.model.solicitud.Solicitud;
import com.pragma.crediya.solicitudes.ports.out.SolicitudRepositoryPort;
import com.pragma.crediya.solicitudes.ports.out.TipoPrestamoQueryPort;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

public class CrearSolicitudUseCase {

    private final TipoPrestamoQueryPort tipoPrestamoPort;
    private final SolicitudRepositoryPort solicitudRepo;
    private final Supplier<Instant> clock;

    public CrearSolicitudUseCase(TipoPrestamoQueryPort tipoPrestamoPort,
                                 SolicitudRepositoryPort solicitudRepo,
                                 Supplier<Instant> clock) {
        this.tipoPrestamoPort = tipoPrestamoPort;
        this.solicitudRepo = solicitudRepo;
        this.clock = clock;
    }

    public Mono<Solicitud> crear(String documento, BigDecimal monto, Integer plazoMeses, UUID tipoPrestamoId) {
        return tipoPrestamoPort.findById(tipoPrestamoId)
                .switchIfEmpty(Mono.error(new TipoPrestamoNoExisteException(tipoPrestamoId)))
                .flatMap(tp -> {
                    if (monto.compareTo(tp.montoMinimo()) < 0 || monto.compareTo(tp.montoMaximo()) > 0) {
                        return Mono.error(new MontoFueraDeRangoException(monto));
                    }
                    var solicitud = Solicitud.nueva(documento, monto, plazoMeses, tipoPrestamoId, clock.get());
                    return solicitudRepo.save(solicitud);
                });
    }
}