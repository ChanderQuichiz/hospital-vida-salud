package com.hospital.pacientes.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.pacientes.application.dto.EstadoRequest;
import com.hospital.pacientes.application.dto.PacienteRequest;
import com.hospital.pacientes.application.dto.PacienteResponse;
import com.hospital.pacientes.application.usecases.ActualizarPacienteUseCase;
import com.hospital.pacientes.application.usecases.CambiarEstadoPacienteUseCase;
import com.hospital.pacientes.application.usecases.ObtenerPacienteUseCase;
import com.hospital.pacientes.application.usecases.RegistrarPacienteUseCase;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final RegistrarPacienteUseCase registrarPacienteUseCase;
    private final ObtenerPacienteUseCase obtenerPacienteUseCase;
    private final ActualizarPacienteUseCase actualizarPacienteUseCase;
    private final CambiarEstadoPacienteUseCase cambiarEstadoPacienteUseCase;

    public PacienteController(RegistrarPacienteUseCase registrarPacienteUseCase,
                              ObtenerPacienteUseCase obtenerPacienteUseCase,
                              ActualizarPacienteUseCase actualizarPacienteUseCase,
                              CambiarEstadoPacienteUseCase cambiarEstadoPacienteUseCase) {
        this.registrarPacienteUseCase = registrarPacienteUseCase;
        this.obtenerPacienteUseCase = obtenerPacienteUseCase;
        this.actualizarPacienteUseCase = actualizarPacienteUseCase;
        this.cambiarEstadoPacienteUseCase = cambiarEstadoPacienteUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PacienteResponse> registrarPaciente(@RequestBody PacienteRequest request) {
        return registrarPacienteUseCase.execute(request);
    }

    @GetMapping("/{id}")
    public Mono<PacienteResponse> obtenerPaciente(@PathVariable Long id) {
        return obtenerPacienteUseCase.execute(id);
    }

    @GetMapping
    public Flux<PacienteResponse> listarPacientes() {
        return obtenerPacienteUseCase.executeListAll();
    }

    @PutMapping("/{id}")
    public Mono<PacienteResponse> actualizarPaciente(@PathVariable Long id, @RequestBody PacienteRequest request) {
        return actualizarPacienteUseCase.execute(id, request);
    }

    @PutMapping("/{id}/estado")
    public Mono<PacienteResponse> cambiarEstado(@PathVariable Long id, @RequestBody EstadoRequest request) {
        return cambiarEstadoPacienteUseCase.execute(id, request.estado());
    }
}
