package com.uniminuto.clinica.apicontroller;
import com.uniminuto.clinica.model.CitaRespDTO;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.service.CitaService;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.model.CitaRq;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/citas")
public class CitaApiController {

    private final CitaService citaService;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public CitaApiController(CitaService citaService,
                             PacienteRepository pacienteRepository,
                             MedicoRepository medicoRepository) {
        this.citaService = citaService;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearCita(@RequestBody CitaRq citaRq) {

        // Validar paciente y médico
        if (!pacienteRepository.existsById(citaRq.getPacienteId())) {
            return ResponseEntity.badRequest().body("El paciente no existe");
        }

        if (!medicoRepository.existsById(citaRq.getMedicoId())) {
            return ResponseEntity.badRequest().body("El médico no existe");
        }

        try {
            // Convertir DTO -> Entidad
            Cita cita = new Cita();
            cita.setPacienteId(citaRq.getPacienteId());
            cita.setMedicoId(citaRq.getMedicoId());
            cita.setFechaHora(LocalDateTime.parse(citaRq.getFechaHora()));
            cita.setEstado(citaRq.getEstado());
            cita.setMotivo(citaRq.getMotivo());

            Cita nuevaCita = citaService.guardarCita(cita);

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al guardar la cita: " + e.getMessage());
        }
    }

@GetMapping("/listar")
public ResponseEntity<List<CitaRespDTO>> listarCitas() {
    try {
        List<CitaRespDTO> citas = citaService.listarCitasConNombres();
        return ResponseEntity.ok(citas);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}


    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerCitaPorId(@PathVariable Long id) {
        try {
            Cita cita = citaService.obtenerPorId(id);
            if (cita == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(cita);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar-desc")
    public ResponseEntity<List<Cita>> listarCitasPorFechaHoraDesc() {
        try {
            return ResponseEntity.ok(citaService.listarCitasPorFechaHoraDesc());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
