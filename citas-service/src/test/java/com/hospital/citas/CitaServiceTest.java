package com.hospital.citas;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.hospital.citas.application.dto.CitaRequest;
import com.hospital.citas.application.dto.PacienteDTO;
import com.hospital.citas.application.ports.EventPublisherPort;
import com.hospital.citas.application.ports.PacientePort;

import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class CitaServiceTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PacientePort pacientePort;

    @MockBean
    private EventPublisherPort eventPublisherPort;

    @Test
    void debeProgramarYConsultarCitaConPacienteValido() {
        PacienteDTO pacienteMock = new PacienteDTO(1L, "DNI", "12345678", "Juan", "Perez", "ACTIVO");
        org.mockito.BDDMockito.given(pacientePort.obtenerPaciente(1L)).willReturn(Mono.just(pacienteMock));
        org.mockito.BDDMockito.given(eventPublisherPort.publicarCitaConfirmada(org.mockito.ArgumentMatchers.any())).willReturn(Mono.empty());

        LocalDateTime fechaFutura = LocalDateTime.now().plusDays(2).withHour(10).withMinute(0).withSecond(0).withNano(0);

        CitaRequest request = new CitaRequest(1L, "Cardiologia", fechaFutura, "DOC-001", "Chequeo de rutina");

        // 1. Programar Cita
        webTestClient.post()
                .uri("/api/citas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.estado").isEqualTo("PENDIENTE")
                .jsonPath("$.especialidad").isEqualTo("Cardiologia");
    }

    @Test
    void debeRechazarTransicionNoPermitida() {
        PacienteDTO pacienteMock = new PacienteDTO(2L, "DNI", "87654321", "Ana", "Ramos", "ACTIVO");
        org.mockito.BDDMockito.given(pacientePort.obtenerPaciente(2L)).willReturn(Mono.just(pacienteMock));
        org.mockito.BDDMockito.given(eventPublisherPort.publicarCitaCancelada(org.mockito.ArgumentMatchers.any())).willReturn(Mono.empty());

        LocalDateTime fechaFutura = LocalDateTime.now().plusDays(3).withHour(11).withMinute(30).withSecond(0).withNano(0);
        CitaRequest request = new CitaRequest(2L, "Pediatria", fechaFutura, "DOC-002", "Consulta");

        // Programar cita
        byte[] responseBody = webTestClient.post()
                .uri("/api/citas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult().getResponseBody();

        String responseStr = new String(responseBody);
        Long citaId = Long.parseLong(responseStr.replaceAll(".*\"id\":(\\d+).*", "$1"));

        // Cancelar cita
        webTestClient.put()
                .uri("/api/citas/" + citaId + "/cancelar")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.estado").isEqualTo("CANCELADA");

        // Intentar confirmar cita cancelada (Debe lanzar 409 Conflict)
        webTestClient.put()
                .uri("/api/citas/" + citaId + "/confirmar")
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("$.codigo").isEqualTo(409);
    }
}
