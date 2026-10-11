package com.hospital.citas.infrastructure.messaging;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.hospital.citas.application.ports.EventPublisherPort;
import com.hospital.citas.application.usecases.ProcesarAtencionRegistradaUseCase;
import com.hospital.citas.domain.entities.Cita;

import reactor.core.publisher.Mono;

@Component
public class RabbitMQAdapter implements EventPublisherPort {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQAdapter.class);

    private final RabbitTemplate rabbitTemplate;
    private final ProcesarAtencionRegistradaUseCase procesarAtencionRegistradaUseCase;

    public RabbitMQAdapter(RabbitTemplate rabbitTemplate, ProcesarAtencionRegistradaUseCase procesarAtencionRegistradaUseCase) {
        this.rabbitTemplate = rabbitTemplate;
        this.procesarAtencionRegistradaUseCase = procesarAtencionRegistradaUseCase;
    }

    @Override
    public Mono<Void> publicarCitaConfirmada(Cita cita) {
        return publicarEvento(cita, "CitaConfirmada", "cita.confirmada", "PENDIENTE", "CONFIRMADA");
    }

    @Override
    public Mono<Void> publicarCitaReprogramada(Cita cita, String fechaAnterior) {
        return publicarEvento(cita, "CitaReprogramada", "cita.reprogramada", fechaAnterior, cita.getFechaHora().toString());
    }

    @Override
    public Mono<Void> publicarCitaCancelada(Cita cita) {
        return publicarEvento(cita, "CitaCancelada", "cita.cancelada", cita.getEstado().name(), "CANCELADA");
    }

    private Mono<Void> publicarEvento(Cita cita, String tipo, String routingKey, String estadoAnt, String estadoNuevo) {
        return Mono.fromRunnable(() -> {
            try {
                Map<String, Object> evento = Map.of(
                    "eventoId", java.util.UUID.randomUUID().toString(),
                    "tipo", tipo,
                    "fechaHora", LocalDateTime.now().toString(),
                    "origen", "citas-service",
                    "recurso", "cita",
                    "recursoId", cita.getId().value(),
                    "estadoAnterior", estadoAnt,
                    "estadoNuevo", estadoNuevo,
                    "datos", Map.of(
                        "citaId", cita.getId().value(),
                        "pacienteId", cita.getPacienteId(),
                        "especialidad", cita.getEspecialidad(),
                        "fechaHoraCita", cita.getFechaHora().toString()
                    )
                );
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, evento);
                log.info("Evento {} publicado para Cita ID: {}", tipo, cita.getId().value());
            } catch (Exception e) {
                log.error("Error publicando evento {}: {}", tipo, e.getMessage());
            }
        });
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ATENCIONES)
    public void escucharAtencionRegistrada(Map<String, Object> mensaje) {
        try {
            Map<String, Object> datos = (Map<String, Object>) mensaje.get("datos");
            if (datos != null && datos.get("citaId") != null) {
                Long citaId = Long.valueOf(datos.get("citaId").toString());
                procesarAtencionRegistradaUseCase.execute(citaId).subscribe();
            }
        } catch (Exception e) {
            log.error("Error consumiendo evento AtencionRegistrada en Citas: {}", e.getMessage());
        }
    }
}
