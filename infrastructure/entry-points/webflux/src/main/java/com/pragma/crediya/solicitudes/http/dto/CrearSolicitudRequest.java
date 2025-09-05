package com.pragma.crediya.solicitudes.http.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public class CrearSolicitudRequest {
    @NotBlank(message = "documento es requerido")
    @Pattern(regexp = "\\d{6,20}", message = "documento debe ser numérico de 6 a 20 dígitos")
    private String documento;

    @NotNull(message = "monto es requerido")
    @DecimalMin(value = "0.01", inclusive = true, message = "monto debe ser > 0")
    @Digits(integer = 16, fraction = 2, message = "monto con max 2 decimales")
    private BigDecimal monto;

    @NotNull(message = "plazoMeses es requerido")
    @Min(value = 1, message = "plazoMeses debe ser >= 1")
    @Max(value = 120, message = "plazoMeses debe ser <= 120")
    private Integer plazoMeses;

    @NotNull(message = "tipoPrestamoId es requerido")
    private UUID tipoPrestamoId;

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public Integer getPlazoMeses() { return plazoMeses; }
    public void setPlazoMeses(Integer plazoMeses) { this.plazoMeses = plazoMeses; }
    public UUID getTipoPrestamoId() { return tipoPrestamoId; }
    public void setTipoPrestamoId(UUID tipoPrestamoId) { this.tipoPrestamoId = tipoPrestamoId; }
}

