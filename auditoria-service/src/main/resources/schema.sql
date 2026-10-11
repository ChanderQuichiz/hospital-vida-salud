CREATE TABLE IF NOT EXISTS auditoria (
    id BIGSERIAL PRIMARY KEY,
    evento_id VARCHAR(100) NOT NULL UNIQUE,
    tipo_evento VARCHAR(100) NOT NULL,
    recurso VARCHAR(50) NOT NULL,
    recurso_id BIGINT NOT NULL,
    estado_anterior VARCHAR(50) NOT NULL,
    estado_nuevo VARCHAR(50) NOT NULL,
    origen VARCHAR(100) NOT NULL,
    fecha TIMESTAMP NOT NULL
);
