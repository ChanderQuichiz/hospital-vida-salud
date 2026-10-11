package com.hospital.citas.presentation;

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

import com.hospital.citas.application.dto.CitaRequest;
import com.hospital.citas.application.dto.CitaResponse;
import com.hospital.citas.application.dto.ReprogramarRequest;
import com.hospital.citas.application.usecases.CancelarCitaUseCase;
import com.hospital.citas.application.usecases.ConfirmarCitaUseCase;
import com.hospital.citas.application.usecases.MarcarAusenteUseCase;
import com.hospital.citas.application.usecases.ProgramarCitaUseCase;
import com.hospital.citas.application.usecases.ReprogramarCitaUseCase;
import com.hospital.citas.domain.repositories.CitaRepository;
import com.hospital.citas.domain.valueobjects.CitaId;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final ProgramarCitaUseCase programarCitaUseCase;
    private final ConfirmarCitaUseCase confirmarCitaUseCase;
    private final ReprogramarCitaUseCase reprogramarCitaUseCase;
    private final CancelarCitaUseCase cancelarCitaUseCase;
    private final MarcarAusenteUseCase marcarAusenteUseCase;
    private final CitaRepository citaRepository;

    public CitaController(ProgramarCitaUseCase programarCitaUseCase,
                          ConfirmarCitaUseCase confirmarCitaUseCase,
                          ReprogramarCitaUseCase reprogramarCitaUseCase,
                          CancelarCitaUseCase cancelarCitaUseCase,
                          MarcarAusenteUseCase marcarAusenteUseCase,
                          CitaRepository citaRepository) {
        this.programarCitaUseCase = programarCitaUseCase;
        this.confirmarCitaUseCase = confirmarCitaUseCase;
        this.reprogramarCitaUseCase = reprogramarCitaUseCase;
        this.cancelarCitaUseCase = cancelarCitaUseCase;
        this.marcarAusenteUseCase = marcarAusenteUseCase;
        this.citaRepository = citaRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CitaResponse> programarCita(@RequestBody CitaRequest request) {
        return programarCitaUseCase.execute(request);
    }

    @GetMapping("/{id}")
    public Mono<CitaResponse> obtenerCita(@PathVariable Long id) {
        return citaRepository.findById(new CitaId(id)).map(CitaResponse::fromDomain);
    }

    @GetMapping
    public Flux<CitaResponse> listarCitasPorPaciente(@RequestParam Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId).map(CitaResponse::fromDomain);
    }

    @PutMapping("/{id}/confirmar")
    public Mono<CitaResponse> confirmarCita(@PathVariable Long id) {
        return confirmarCitaUseCase.execute(id);
    }

    @PutMapping("/{id}/reprogramar")
    public Mono<CitaResponse> reprogramarCita(@PathVariable Long id, @RequestBody ReprogramarRequest request) {
        return reprogramarCitaUseCase.execute(id, request);
    }

    @PutMapping("/{id}/cancelar")
    public Mono<CitaResponse> cancelarCita(@PathVariable Long id) {
        return cancelarCitaUseCase.execute(id);
    }

    @PutMapping("/{id}/ausente")
    public Mono<CitaResponse> marcarAusente(@PathVariable Long id) {
        return marcarAusenteUseCase.execute(id);
    }
}
