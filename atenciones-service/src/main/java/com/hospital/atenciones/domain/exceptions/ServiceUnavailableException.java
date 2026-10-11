package com.hospital.atenciones.domain.exceptions;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String serviceName) {
        super("El servicio " + serviceName + " no esta disponible en este momento");
    }
}
