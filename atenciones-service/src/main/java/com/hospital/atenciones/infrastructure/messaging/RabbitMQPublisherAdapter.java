package com.hospital.atenciones.infrastructure.messaging;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.hospital.atenciones.application.ports.EventPublisherPort;
import com.hospital.atenciones.domain.entities.Atencion;

import reactor.core.publisher.Mono;

@Component
public class RabbitMQPublisherAdapter implements EventPublisherPort {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQPublisherAdapter.class);

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQPublisherAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Mono<Void> publicarAtencionRegistrada(Atencion atencion) {
        return Mono.fromRunnable(() -> {
            try {
                Map<String, Object> evento = Map.of(
                    "eventoId", UUID.randomUUID().toString(),
                    "tipo", "AtencionRegistrada",
                    "fechaHora", LocalDateTime.now().toString(),
                    "origen", "atenciones-service",
                    "recurso", "atencion",
                    "recursoId", atencion.getId().value(),
                    "estadoAnterior", "N/A",
                    "estadoNuevo", atencion.getEstado().name(),
                    "datos", Map.of(
                        "atencionId", atencion.getId().value(),
                        "citaId", atencion.getCitaId(),
                        "pacienteId", atencion.getPacienteId(),
                        "area", atencion.getArea()
                    )
                );
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "atencion.registrada", evento);
                log.info("Evento AtencionRegistrada publicado para Atencion ID: {}", atencion.getId().value());
            } catch (Exception e) {
                log.error("Error publicando evento AtencionRegistrada: {}", e.getMessage());
            }
        });
    }
}
