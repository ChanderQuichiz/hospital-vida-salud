package com.hospital.notificaciones.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.notificaciones.application.dto.NotificacionResponse;
import com.hospital.notificaciones.application.usecases.ConsultarNotificacionesUseCase;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final ConsultarNotificacionesUseCase consultarNotificacionesUseCase;

    public NotificacionController(ConsultarNotificacionesUseCase consultarNotificacionesUseCase) {
        this.consultarNotificacionesUseCase = consultarNotificacionesUseCase;
    }

    @GetMapping
    public Flux<NotificacionResponse> listarNotificaciones(@RequestParam Long pacienteId) {
        return consultarNotificacionesUseCase.execute(pacienteId);
    }
}
