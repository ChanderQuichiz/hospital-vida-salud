package com.hospital.pacientes.infrastructure.messaging;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.hospital.pacientes.application.ports.EventPublisherPort;
import com.hospital.pacientes.domain.entities.Paciente;

import reactor.core.publisher.Mono;

@Component
public class RabbitMQPublisherAdapter implements EventPublisherPort {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQPublisherAdapter.class);

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQPublisherAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Mono<Void> publicarPacienteRegistrado(Paciente paciente) {
        return Mono.fromRunnable(() -> {
            try {
                Map<String, Object> evento = Map.of(
                    "eventoId", UUID.randomUUID().toString(),
                    "tipo", "PacienteRegistrado",
                    "fechaHora", LocalDateTime.now().toString(),
                    "origen", "pacientes-service",
                    "recurso", "paciente",
                    "recursoId", paciente.getId().value(),
                    "estadoAnterior", "N/A",
                    "estadoNuevo", paciente.getEstado().name(),
                    "datos", Map.of(
                        "pacienteId", paciente.getId().value(),
                        "nombres", paciente.getNombres(),
                        "apellidos", paciente.getApellidos(),
                        "tipoDocumento", paciente.getDocumento().tipo(),
                        "numeroDocumento", paciente.getDocumento().numero()
                    )
                );
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "paciente.registrado", evento);
                log.info("Evento PacienteRegistrado publicado para Paciente ID: {}", paciente.getId().value());
            } catch (Exception e) {
                log.error("Error publicando evento PacienteRegistrado: {}", e.getMessage());
            }
        });
    }
}
