package com.pragma.crediya.solicitudes.persistence.repository;

import com.pragma.crediya.solicitudes.persistence.data.TipoPrestamoData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface TipoPrestamoReactiveRepository extends ReactiveCrudRepository<TipoPrestamoData, UUID> {}

