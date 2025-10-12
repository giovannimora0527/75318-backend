package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.RecetaApi;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.service.RecetaService;
import com.uniminuto.clinica.repository.MedicamentoRepository;
import com.uniminuto.clinica.repository.CitaRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/receta")
public class RecetaApiController implements RecetaApi {

    private final RecetaService recetaService;
    private final MedicamentoRepository medicamentoRepository;
    private final CitaRepository citaRepository;

    public RecetaApiController(RecetaService recetaService,
                               MedicamentoRepository medicamentoRepository,
                               CitaRepository citaRepository) {
        this.recetaService = recetaService;
        this.medicamentoRepository = medicamentoRepository;
        this.citaRepository = citaRepository;
    }

    @Override
    @PostMapping("/crear")
    public ResponseEntity<?> crearReceta(@RequestBody Receta receta) {

    if (receta.getId() != null && recetaService.obtenerPorId(receta.getId()) != null) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body("La receta con ese ID ya existe");
    }

    if (!medicamentoRepository.existsById(receta.getMedicamentoId().longValue())) {
        return ResponseEntity.badRequest().body("El medicamento no existe");
    }

    // Validar cita
    if (!citaRepository.existsById(receta.getCitaId())) {
        return ResponseEntity.badRequest().body("La cita no existe");
    }

    try {
        Receta nuevaReceta = recetaService.guardarReceta(receta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReceta);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error al guardar la receta: " + e.getMessage());
    }
}


    @Override
    @GetMapping("/listar")
    public List<Receta> listarReceta() {
        return recetaService.obtenerTodas();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Receta> obtenerRecetaPorId(@PathVariable Long id) {
    try {
        Receta receta = recetaService.obtenerPorId(id);
        if (receta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(receta);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(null);
    }
    }

    @Override
    @GetMapping("/listar-desc")
    public ResponseEntity<List<Receta>> listarRecetaPorFechaCreacionDesc() {
        try {
            List<Receta> recetas = recetaService.listarRecetaPorFechaCreacionDesc();
            return ResponseEntity.ok(recetas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
