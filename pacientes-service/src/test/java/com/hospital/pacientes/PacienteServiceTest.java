package com.hospital.pacientes;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.hospital.pacientes.application.dto.PacienteRequest;
import com.hospital.pacientes.application.ports.EventPublisherPort;

import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class PacienteServiceTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EventPublisherPort eventPublisherPort;

    @Test
    void debeRegistrarPacienteValido() {
        org.mockito.BDDMockito.given(eventPublisherPort.publicarPacienteRegistrado(org.mockito.ArgumentMatchers.any()))
                .willReturn(Mono.empty());

        PacienteRequest request = new PacienteRequest(
            "DNI", "11223344", "Juan", "Perez",
            LocalDate.of(1990, 5, 15), "987654321", "juan.perez@email.com", "Av. Peru 123"
        );

        webTestClient.post()
                .uri("/api/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.nombres").isEqualTo("Juan")
                .jsonPath("$.numeroDocumento").isEqualTo("11223344")
                .jsonPath("$.estado").isEqualTo("ACTIVO");
    }

    @Test
    void debeRechazarPacienteDuplicado() {
        org.mockito.BDDMockito.given(eventPublisherPort.publicarPacienteRegistrado(org.mockito.ArgumentMatchers.any()))
                .willReturn(Mono.empty());

        PacienteRequest request = new PacienteRequest(
            "DNI", "99887766", "Maria", "Gomez",
            LocalDate.of(1992, 8, 20), "912345678", "maria@email.com", "Calle Lima 456"
        );

        // Primer registro exitoso
        webTestClient.post()
                .uri("/api/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated();

        // Segundo registro del mismo documento debe fallar con 409 Conflict
        webTestClient.post()
                .uri("/api/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("$.codigo").isEqualTo(409);
    }
}
