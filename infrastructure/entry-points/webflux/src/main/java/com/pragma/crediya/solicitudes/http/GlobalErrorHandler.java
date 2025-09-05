package com.pragma.crediya.solicitudes.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.crediya.solicitudes.error.DominioInvalidoException;
import com.pragma.crediya.solicitudes.error.MontoFueraDeRangoException;
import com.pragma.crediya.solicitudes.error.TipoPrestamoNoExisteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

@Component
@Order(-2)
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalErrorHandler.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status =
                (ex instanceof TipoPrestamoNoExisteException) ? HttpStatus.NOT_FOUND :
                        (ex instanceof MontoFueraDeRangoException)   ? HttpStatus.UNPROCESSABLE_ENTITY :
                                (ex instanceof DominioInvalidoException
                                        || ex instanceof IllegalArgumentException)  ? HttpStatus.BAD_REQUEST :
                                        HttpStatus.INTERNAL_SERVER_ERROR;

        if (status.is5xxServerError()) {
            log.error("Unhandled error", ex);
        } else if (status == HttpStatus.BAD_REQUEST) {
            log.warn("Validation error: {}", ex.getMessage());
        } else {
            log.info("Domain error: {} -> {}", ex.getClass().getSimpleName(), ex.getMessage());
        }

        var problem = Map.of(
                "type", "about:blank",
                "title", status.getReasonPhrase(),
                "status", status.value(),
                "detail", ex.getMessage(),
                "timestamp", Instant.now().toString(),
                "path", exchange.getRequest().getPath().value()
        );

        var response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_PROBLEM_JSON);

        try {
            var bytes = mapper.writeValueAsBytes(problem);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
        } catch (Exception e) {
            return response.setComplete();
        }
    }
}
