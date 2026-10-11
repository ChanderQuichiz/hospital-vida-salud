package com.hospital.auditoria;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.hospital.auditoria.application.usecases.RegistrarAuditoriaUseCase;

import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class AuditoriaServiceTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RegistrarAuditoriaUseCase registrarAuditoriaUseCase;

    @Test
    void debeRegistrarYConsultarAuditoria() {
        Map<String, Object> evento = Map.of(
            "eventoId", "evt-aud-001",
            "tipo", "CitaConfirmada",
            "origen", "citas-service",
            "recurso", "cita",
            "recursoId", 100L,
            "estadoAnterior", "PENDIENTE",
            "estadoNuevo", "CONFIRMADA"
        );

        StepVerifier.create(registrarAuditoriaUseCase.execute(evento))
                .verifyComplete();

        webTestClient.get()
                .uri("/api/auditoria?recurso=cita&recursoId=100")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].eventoId").isEqualTo("evt-aud-001")
                .jsonPath("$[0].recurso").isEqualTo("cita")
                .jsonPath("$[0].recursoId").isEqualTo(100)
                .jsonPath("$[0].estadoNuevo").isEqualTo("CONFIRMADA");
    }
}
