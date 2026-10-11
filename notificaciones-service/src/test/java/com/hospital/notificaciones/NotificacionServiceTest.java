package com.hospital.notificaciones;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.hospital.notificaciones.application.usecases.ProcesarNotificacionEventoUseCase;

import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class NotificacionServiceTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProcesarNotificacionEventoUseCase procesarNotificacionEventoUseCase;

    @Test
    void debeProcesarYConsultarNotificacionDeEvento() {
        Map<String, Object> evento = Map.of(
            "eventoId", "evt-12345",
            "tipo", "CitaConfirmada",
            "datos", Map.of(
                "pacienteId", 5L,
                "citaId", 100L,
                "especialidad", "Dermatologia",
                "fechaHoraCita", "2026-10-20T11:00:00"
            )
        );

        // Procesar evento reactivo
        StepVerifier.create(procesarNotificacionEventoUseCase.execute(evento))
                .verifyComplete();

        // Consultar notificaciones por pacienteId vía API
        webTestClient.get()
                .uri("/api/notificaciones?pacienteId=5")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].eventoId").isEqualTo("evt-12345")
                .jsonPath("$[0].pacienteId").isEqualTo(5)
                .jsonPath("$[0].tipo").isEqualTo("CitaConfirmada");
    }
}
