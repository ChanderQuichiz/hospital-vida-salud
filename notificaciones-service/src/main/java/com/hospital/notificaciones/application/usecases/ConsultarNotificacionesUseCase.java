package com.hospital.notificaciones.application.usecases;

import org.springframework.stereotype.Service;

import com.hospital.notificaciones.application.dto.NotificacionResponse;
import com.hospital.notificaciones.domain.repositories.NotificacionRepository;

import reactor.core.publisher.Flux;

@Service
public class ConsultarNotificacionesUseCase {

    private final NotificacionRepository notificacionRepository;

    public ConsultarNotificacionesUseCase(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public Flux<NotificacionResponse> execute(Long pacienteId) {
        return notificacionRepository.findByPacienteId(pacienteId).map(NotificacionResponse::fromDomain);
    }
}
