package com.hospital.auditoria.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.auditoria.application.dto.AuditoriaResponse;
import com.hospital.auditoria.application.usecases.ConsultarAuditoriaUseCase;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaController {

    private final ConsultarAuditoriaUseCase consultarAuditoriaUseCase;

    public AuditoriaController(ConsultarAuditoriaUseCase consultarAuditoriaUseCase) {
        this.consultarAuditoriaUseCase = consultarAuditoriaUseCase;
    }

    @GetMapping
    public Flux<AuditoriaResponse> listarAuditoria(@RequestParam String recurso, @RequestParam Long recursoId) {
        return consultarAuditoriaUseCase.execute(recurso, recursoId);
    }
}
