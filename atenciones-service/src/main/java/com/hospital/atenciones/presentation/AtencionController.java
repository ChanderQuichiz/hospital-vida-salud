package com.hospital.atenciones.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.atenciones.application.dto.AtencionRequest;
import com.hospital.atenciones.application.dto.AtencionResponse;
import com.hospital.atenciones.application.usecases.AnularAtencionUseCase;
import com.hospital.atenciones.application.usecases.ObtenerAtencionUseCase;
import com.hospital.atenciones.application.usecases.RegistrarAtencionUseCase;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/atenciones")
public class AtencionController {

    private final RegistrarAtencionUseCase registrarAtencionUseCase;
    private final AnularAtencionUseCase anularAtencionUseCase;
    private final ObtenerAtencionUseCase obtenerAtencionUseCase;

    public AtencionController(RegistrarAtencionUseCase registrarAtencionUseCase,
                              AnularAtencionUseCase anularAtencionUseCase,
                              ObtenerAtencionUseCase obtenerAtencionUseCase) {
        this.registrarAtencionUseCase = registrarAtencionUseCase;
        this.anularAtencionUseCase = anularAtencionUseCase;
        this.obtenerAtencionUseCase = obtenerAtencionUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AtencionResponse> registrarAtencion(@RequestBody AtencionRequest request) {
        return registrarAtencionUseCase.execute(request);
    }

    @GetMapping("/{id}")
    public Mono<AtencionResponse> obtenerAtencion(@PathVariable Long id) {
        return obtenerAtencionUseCase.execute(id);
    }

    @GetMapping
    public Flux<AtencionResponse> listarAtencionesPorPaciente(@RequestParam Long pacienteId) {
        return obtenerAtencionUseCase.executeListByPacienteId(pacienteId);
    }

    @PutMapping("/{id}/anular")
    public Mono<AtencionResponse> anularAtencion(@PathVariable Long id) {
        return anularAtencionUseCase.execute(id);
    }
}
