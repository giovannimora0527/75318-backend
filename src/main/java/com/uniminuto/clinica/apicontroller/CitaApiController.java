package com.uniminuto.clinica.apicontroller;
import com.uniminuto.clinica.api.CitaApi;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.service.CitaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaApiController implements CitaApi {

    private final CitaService citaService;

    public CitaApiController(CitaService citaService) {
        this.citaService = citaService;
    }

    @Override
    @PostMapping("/crear")
    public Cita crearCita(@RequestBody Cita cita) {
        return citaService.guardarCita(cita);
    }

    @Override
    @GetMapping("/listar")
    public List<Cita> listarCitas() {
        return citaService.obtenerTodas();
    }

    @Override
    @GetMapping("/{id}")
    public Cita obtenerCitaPorId(@PathVariable Long id) {
        return citaService.obtenerPorId(id);
    }

    @Override
    @GetMapping("/listar-desc")
    public ResponseEntity<List<Cita>> listarCitasPorFechaHoraDesc() {
    return ResponseEntity.ok(citaService.listarCitasPorFechaHoraDesc());
}

}
