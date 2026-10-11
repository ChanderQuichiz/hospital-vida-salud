CREATE TABLE IF NOT EXISTS cita (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    paciente_id BIGINT NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    fecha_hora TIMESTAMP NOT NULL,
    profesional_codigo VARCHAR(50) NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_cita_profesional_agenda ON cita (profesional_codigo, fecha_hora);
CREATE INDEX IF NOT EXISTS idx_cita_paciente_agenda ON cita (paciente_id, fecha_hora);
