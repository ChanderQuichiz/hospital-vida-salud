package com.hospital.notificaciones.infrastructure.messaging;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.hospital.notificaciones.application.usecases.ProcesarNotificacionEventoUseCase;

@Component
public class RabbitMQListenerAdapter {

    private final ProcesarNotificacionEventoUseCase procesarNotificacionEventoUseCase;

    public RabbitMQListenerAdapter(ProcesarNotificacionEventoUseCase procesarNotificacionEventoUseCase) {
        this.procesarNotificacionEventoUseCase = procesarNotificacionEventoUseCase;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_CITAS)
    public void escucharEventosCita(Map<String, Object> mensaje) {
        procesarNotificacionEventoUseCase.execute(mensaje).subscribe();
    }
}
