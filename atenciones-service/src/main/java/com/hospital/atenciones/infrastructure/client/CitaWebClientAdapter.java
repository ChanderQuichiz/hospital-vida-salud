package com.hospital.atenciones.infrastructure.client;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.hospital.atenciones.application.dto.CitaDTO;
import com.hospital.atenciones.application.ports.CitaPort;
import com.hospital.atenciones.domain.exceptions.AtencionNotFoundException;
import com.hospital.atenciones.domain.exceptions.ServiceUnavailableException;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
public class CitaWebClientAdapter implements CitaPort {

    private static final Logger log = LoggerFactory.getLogger(CitaWebClientAdapter.class);

    private final WebClient.Builder webClientBuilder;

    public CitaWebClientAdapter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<CitaDTO> obtenerCita(Long citaId) {
        return webClientBuilder.build()
                .get()
                .uri("http://citas-service/api/citas/{id}", citaId)
                .retrieve()
                .bodyToMono(CitaDTO.class)
                .timeout(Duration.ofSeconds(3))
                .retryWhen(Retry.max(1).filter(ex -> !(ex instanceof WebClientResponseException.NotFound)))
                .onErrorMap(WebClientResponseException.NotFound.class, ex -> new AtencionNotFoundException(citaId))
                .onErrorMap(ex -> !(ex instanceof AtencionNotFoundException), ex -> {
                    log.error("Error consultando citas-service: {}", ex.getMessage());
                    return new ServiceUnavailableException("citas-service");
                });
    }
}
