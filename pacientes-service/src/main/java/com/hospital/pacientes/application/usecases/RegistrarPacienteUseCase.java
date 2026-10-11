package com.hospital.pacientes.application.usecases;

import org.springframework.stereotype.Service;

import com.hospital.pacientes.application.dto.PacienteRequest;
import com.hospital.pacientes.application.dto.PacienteResponse;
import com.hospital.pacientes.application.ports.EventPublisherPort;
import com.hospital.pacientes.domain.entities.Paciente;
import com.hospital.pacientes.domain.exceptions.PacienteDuplicadoException;
import com.hospital.pacientes.domain.repositories.PacienteRepository;
import com.hospital.pacientes.domain.valueobjects.Documento;

import reactor.core.publisher.Mono;

@Service
public class RegistrarPacienteUseCase {

    private final PacienteRepository pacienteRepository;
    private final EventPublisherPort eventPublisherPort;

    public RegistrarPacienteUseCase(PacienteRepository pacienteRepository, EventPublisherPort eventPublisherPort) {
        this.pacienteRepository = pacienteRepository;
        this.eventPublisherPort = eventPublisherPort;
    }

    public Mono<PacienteResponse> execute(PacienteRequest request) {
        Documento documento = new Documento(request.tipoDocumento(), request.numeroDocumento());

        return pacienteRepository.existsByDocumento(documento)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new PacienteDuplicadoException(request.tipoDocumento(), request.numeroDocumento()));
                    }

                    Paciente paciente = Paciente.create(
                        documento,
                        request.nombres(),
                        request.apellidos(),
                        request.fechaNacimiento(),
                        request.telefono(),
                        request.correo(),
                        request.direccion()
                    );

                    return pacienteRepository.save(paciente)
                            .flatMap(guardado -> 
                                eventPublisherPort.publicarPacienteRegistrado(guardado)
                                        .thenReturn(PacienteResponse.fromDomain(guardado))
                            );
                });
    }
}
