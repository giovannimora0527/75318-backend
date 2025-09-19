package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.CitaApi;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CitaApiController implements CitaApi {

    private final CitaService citaService;

    @Override
    public ResponseEntity<RespuestaRs<Cita>> guardarCita(CitaRq citaRq) {
        return ResponseEntity.ok(citaService.guardarCita(citaRq));
    }

    @Override
    public ResponseEntity<List<Cita>> listarCitas() {
        return ResponseEntity.ok(citaService.listarCitas());
    }

    @Override
    public ResponseEntity<List<Cita>> listarCitasOrdenadosPorFecha() {
        List<Cita> citas = citaService.listarCitasOrdenadosPorFecha();
        return ResponseEntity.ok(citas);    }
}

