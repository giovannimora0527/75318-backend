package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.CitaApi;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author jartunduaga
 */
@RestController
public class CitaApiController implements CitaApi {

    @Autowired
    private CitaService citaService;

    @Override
    public ResponseEntity<Cita> guardarCita(Long pacienteId, Long medicoId, Cita cita){
        Cita nuevaCita = citaService.guardarCita(pacienteId, medicoId, cita);
        return ResponseEntity.ok(nuevaCita);
    }

    @Override
    public ResponseEntity<List<Cita>> listarCitaPorFecha(String orden) {
        return ResponseEntity.ok(this.citaService.listarCitaPorFecha(orden));
    }
}
