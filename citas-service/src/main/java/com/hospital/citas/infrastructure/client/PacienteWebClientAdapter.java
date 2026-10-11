package com.hospital.citas.infrastructure.client;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.hospital.citas.application.dto.PacienteDTO;
import com.hospital.citas.application.ports.PacientePort;
import com.hospital.citas.domain.exceptions.CitaNotFoundException;
import com.hospital.citas.domain.exceptions.ServiceUnavailableException;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
public class PacienteWebClientAdapter implements PacientePort {

    private static final Logger log = LoggerFactory.getLogger(PacienteWebClientAdapter.class);

    private final WebClient.Builder webClientBuilder;

    public PacienteWebClientAdapter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<PacienteDTO> obtenerPaciente(Long pacienteId) {
        return webClientBuilder.build()
                .get()
                .uri("http://pacientes-service/api/pacientes/{id}", pacienteId)
                .retrieve()
                .bodyToMono(PacienteDTO.class)
                .timeout(Duration.ofSeconds(3))
                .retryWhen(Retry.max(1).filter(ex -> !(ex instanceof WebClientResponseException.NotFound)))
                .onErrorMap(WebClientResponseException.NotFound.class, ex -> new CitaNotFoundException(pacienteId))
                .onErrorMap(ex -> !(ex instanceof CitaNotFoundException), ex -> {
                    log.error("Error consultando pacientes-service: {}", ex.getMessage());
                    return new ServiceUnavailableException("pacientes-service");
                });
    }
}
