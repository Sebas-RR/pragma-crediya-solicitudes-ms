package com.pragma.crediya.solicitudes.http;

import com.pragma.crediya.solicitudes.usecase.solicitud.CrearSolicitudUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SolicitudRouter {
    @Bean
    public TransactionalOperator transactionalOperator(ReactiveTransactionManager txManager) {
        return TransactionalOperator.create(txManager);
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public SolicitudHandler solicitudHandler(CrearSolicitudUseCase useCase,
                                             TransactionalOperator tx,
                                             LocalValidatorFactoryBean validator) {
        return new SolicitudHandler(useCase, tx, validator);
    }

    @Bean
    public RouterFunction<ServerResponse> solicitudRoutes(SolicitudHandler handler) {
        return RouterFunctions.route()
                .POST("/api/v1/solicitud", handler::crear)
                .build();
    }
}
