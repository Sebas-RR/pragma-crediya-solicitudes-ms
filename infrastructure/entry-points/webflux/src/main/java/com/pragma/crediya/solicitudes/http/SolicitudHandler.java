package com.pragma.crediya.solicitudes.http;

import com.pragma.crediya.solicitudes.http.dto.CrearSolicitudRequest;
import com.pragma.crediya.solicitudes.http.dto.SolicitudResponse;
import com.pragma.crediya.solicitudes.model.solicitud.Solicitud;
import com.pragma.crediya.solicitudes.usecase.solicitud.CrearSolicitudUseCase;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class SolicitudHandler {

    private static final Logger log = LoggerFactory.getLogger(SolicitudHandler.class);

    private final CrearSolicitudUseCase useCase;
    private final TransactionalOperator tx;
    private final SpringValidatorAdapter validatorAdapter;

    public SolicitudHandler(CrearSolicitudUseCase useCase,
                            TransactionalOperator tx,
                            Validator validator) {
        this.useCase = useCase;
        this.tx = tx;
        this.validatorAdapter = new SpringValidatorAdapter(validator);
    }

    public Mono<ServerResponse> crear(ServerRequest req) {
        final String requestId = req.headers().firstHeader("X-Request-Id");
        final long start = System.nanoTime();

        return req.bodyToMono(CrearSolicitudRequest.class)
                .flatMap(this::validate) // Bean Validation del DTO
                .doOnNext(r -> log.info("rid={} POST /api/v1/solicitud doc={} tipo={}", requestId, r.getDocumento(), r.getTipoPrestamoId()))
                .flatMap(r -> tx.transactional(
                        useCase.crear(r.getDocumento(), r.getMonto(), r.getPlazoMeses(), r.getTipoPrestamoId())
                ))
                .map(this::toResponse)
                .flatMap(resp -> ServerResponse.created(req.uri()).contentType(MediaType.APPLICATION_JSON).bodyValue(resp))
                .doOnSuccess(x -> log.info("rid={} created inMs={}", requestId, (System.nanoTime()-start)/1_000_000))
                .doOnError(e -> log.warn("rid={} failed: {}", requestId, e.toString()));
    }


    private Mono<CrearSolicitudRequest> validate(CrearSolicitudRequest body) {
        var errors = new BeanPropertyBindingResult(body, "crearSolicitudRequest");
        validatorAdapter.validate(body, errors);
        if (errors.hasErrors()) {
            return Mono.error(new IllegalArgumentException(errors.toString()));
        }
        return Mono.just(body);
    }

    private SolicitudResponse toResponse(Solicitud s) {
        return new SolicitudResponse(
                s.getId(),
                s.getEstado().name(),
                s.getDocumento(),
                s.getMonto(),
                s.getPlazoMeses(),
                s.getTipoPrestamoId(),
                s.getCreatedAt()
        );
    }
}

