package com.pragma.crediya.solicitudes.persistence.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Table("tipo_prestamo")
public record TipoPrestamoData(
        @Id UUID id_tipo_prestamo,
        String nombre,
        BigDecimal monto_minimo,
        BigDecimal monto_maximo,
        Double tasa_interes,
        Boolean validacion_auto
) {}
