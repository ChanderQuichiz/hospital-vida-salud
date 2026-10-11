CREATE TABLE IF NOT EXISTS notificacion (
    id BIGSERIAL PRIMARY KEY,
    evento_id VARCHAR(100) NOT NULL UNIQUE,
    paciente_id BIGINT NOT NULL,
    cita_id BIGINT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    mensaje VARCHAR(500) NOT NULL,
    fecha_envio TIMESTAMP NOT NULL
);
