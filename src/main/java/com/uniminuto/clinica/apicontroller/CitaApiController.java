package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.CitaApi;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.service.CitaService;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaApiController implements CitaApi {

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

    @Override
@PostMapping("/crear")
public ResponseEntity<?> crearCita(@RequestBody Cita cita) {

    if (cita.getId() != null && citaService.obtenerPorId(cita.getId()) != null) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body("La cita con ese ID ya existe");
    }

    if (!pacienteRepository.existsById(cita.getPacienteId())) {
        return ResponseEntity.badRequest().body("El paciente no existe");
    }

    if (!medicoRepository.existsById(cita.getMedicoId())) {
        return ResponseEntity.badRequest().body("El médico no existe");
    }

    try {
        Cita nuevaCita = citaService.guardarCita(cita);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error al guardar la cita: " + e.getMessage());
    }
}


    @Override
    @GetMapping("/listar")
    public List<Cita> listarCitas() {
        return citaService.obtenerTodas();
    }

    @Override
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

    @Override
    @GetMapping("/listar-desc")
    public ResponseEntity<List<Cita>> listarCitasPorFechaHoraDesc() {
        try {
            return ResponseEntity.ok(citaService.listarCitasPorFechaHoraDesc());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
