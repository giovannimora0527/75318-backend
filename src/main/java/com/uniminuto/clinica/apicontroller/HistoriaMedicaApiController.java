package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.HistoriaMedica;
import com.uniminuto.clinica.model.HistoriaMedicaRq;
import com.uniminuto.clinica.service.HistoriaMedicaService;
import com.uniminuto.clinica.repository.PacienteRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/historia")
public class HistoriaMedicaApiController {

    private final HistoriaMedicaService historiaMedicaService;
    private final PacienteRepository pacienteRepository;

    public HistoriaMedicaApiController(HistoriaMedicaService historiaMedicaService,
                                       PacienteRepository pacienteRepository) {
        this.historiaMedicaService = historiaMedicaService;
        this.pacienteRepository = pacienteRepository;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearHistoriaMedica(@RequestBody HistoriaMedicaRq historiaRq) {

        if (!pacienteRepository.existsById(historiaRq.getPacienteId())) {
            return ResponseEntity.badRequest().body("El paciente no existe");
        }

        try {
            HistoriaMedica historia = new HistoriaMedica();
            historia.setPaciente_id(historiaRq.getPacienteId());  // mapeo al nombre real en la entidad
            historia.setFechaCreacion(historiaRq.getFechaCreacion() != null
                    ? historiaRq.getFechaCreacion()
                    : LocalDateTime.now());

            HistoriaMedica nuevaHistoria = historiaMedicaService.guardarHistoriaMedica(historia);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaHistoria);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al guardar la historia médica: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public List<HistoriaMedica> listarHistoriaMedica() {
        return historiaMedicaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriaMedica> obtenerHistoriaMedicaPorId(@PathVariable Long id) {
        HistoriaMedica historia = historiaMedicaService.obtenerPorId(id);
        if (historia == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(historia);
    }
}
