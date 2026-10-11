CREATE TABLE IF NOT EXISTS paciente (
    id BIGSERIAL PRIMARY KEY,
    tipo_documento VARCHAR(20) NOT NULL,
    numero_documento VARCHAR(30) NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    direccion VARCHAR(200),
    fecha_registro TIMESTAMP NOT NULL,
    estado VARCHAR(20) NOT NULL,
    CONSTRAINT uk_paciente_documento UNIQUE (tipo_documento, numero_documento)
);
