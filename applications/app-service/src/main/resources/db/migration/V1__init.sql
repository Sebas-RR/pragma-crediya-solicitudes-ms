CREATE TABLE IF NOT EXISTS tipo_prestamo (
  id_tipo_prestamo UUID PRIMARY KEY,
  nombre           VARCHAR(100) NOT NULL,
  monto_minimo     NUMERIC(18,2) NOT NULL,
  monto_maximo     NUMERIC(18,2) NOT NULL,
  tasa_interes     NUMERIC(5,2)  NOT NULL,
  validacion_auto  BOOLEAN       NOT NULL DEFAULT true
);

CREATE TABLE IF NOT EXISTS solicitud (
  id_solicitud     UUID PRIMARY KEY,
  documento        VARCHAR(20)   NOT NULL,
  monto            NUMERIC(18,2) NOT NULL CHECK (monto > 0),
  plazo_meses      INT           NOT NULL CHECK (plazo_meses > 0),
  id_tipo_prestamo UUID          NOT NULL REFERENCES tipo_prestamo(id_tipo_prestamo),
  estado           VARCHAR(40)   NOT NULL,
  created_at       TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_solicitud_documento ON solicitud(documento);
CREATE INDEX IF NOT EXISTS idx_solicitud_estado ON solicitud(estado);
