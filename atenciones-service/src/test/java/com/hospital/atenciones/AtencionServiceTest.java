package com.hospital.atenciones;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.hospital.atenciones.application.dto.AtencionRequest;
import com.hospital.atenciones.application.dto.CitaDTO;
import com.hospital.atenciones.application.ports.CitaPort;
import com.hospital.atenciones.application.ports.EventPublisherPort;

import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class AtencionServiceTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CitaPort citaPort;

    @MockBean
    private EventPublisherPort eventPublisherPort;

    @Test
    void debeRegistrarAtencionConCitaConfirmada() {
        CitaDTO citaMock = new CitaDTO(10L, 1L, "CONFIRMADA", "Cardiologia");
        org.mockito.BDDMockito.given(citaPort.obtenerCita(10L)).willReturn(Mono.just(citaMock));
        org.mockito.BDDMockito.given(eventPublisherPort.publicarAtencionRegistrada(org.mockito.ArgumentMatchers.any())).willReturn(Mono.empty());

        AtencionRequest request = new AtencionRequest(10L, "Consultorio 301");

        webTestClient.post()
                .uri("/api/atenciones")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.citaId").isEqualTo(10)
                .jsonPath("$.area").isEqualTo("Consultorio 301")
                .jsonPath("$.estado").isEqualTo("REGISTRADA");
    }

    @Test
    void debeRechazarAtencionSiCitaNoEstaConfirmada() {
        CitaDTO citaPendienteMock = new CitaDTO(20L, 1L, "PENDIENTE", "Cardiologia");
        org.mockito.BDDMockito.given(citaPort.obtenerCita(20L)).willReturn(Mono.just(citaPendienteMock));

        AtencionRequest request = new AtencionRequest(20L, "Consultorio 302");

        webTestClient.post()
                .uri("/api/atenciones")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("$.codigo").isEqualTo(409);
    }
}
