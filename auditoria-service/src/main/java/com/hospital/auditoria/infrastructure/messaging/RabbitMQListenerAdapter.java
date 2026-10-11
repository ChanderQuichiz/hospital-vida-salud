package com.hospital.auditoria.infrastructure.messaging;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.hospital.auditoria.application.usecases.RegistrarAuditoriaUseCase;

@Component
public class RabbitMQListenerAdapter {

    private final RegistrarAuditoriaUseCase registrarAuditoriaUseCase;

    public RabbitMQListenerAdapter(RegistrarAuditoriaUseCase registrarAuditoriaUseCase) {
        this.registrarAuditoriaUseCase = registrarAuditoriaUseCase;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_AUDITORIA)
    public void escucharEventos(Map<String, Object> mensaje) {
        registrarAuditoriaUseCase.execute(mensaje).subscribe();
    }
}
