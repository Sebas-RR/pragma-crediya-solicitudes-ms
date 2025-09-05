package com.pragma.crediya.solicitudes.persistence.repository;

import com.pragma.crediya.solicitudes.persistence.data.SolicitudData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface SolicitudReactiveRepository extends ReactiveCrudRepository<SolicitudData, UUID> {}
