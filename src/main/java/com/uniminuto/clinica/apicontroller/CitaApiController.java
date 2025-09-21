package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.CitaApi;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRs;
import com.uniminuto.clinica.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author JAVIER-CUERVO
 */
@RestController
public class CitaApiController implements CitaApi {

    @Autowired
    private CitaService citaService;


    @Override
    public ResponseEntity<CitaRs<Cita>> guardarCita(Cita cita) {
        Cita citaGuardada = citaService.guardarCita(cita);

        CitaRs<Cita> response = new CitaRs<>();
        response.setStatus(200);
        response.setMensaje("Cita guardada exitosamente");
        response.setData(citaGuardada);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CitaRs<List<Cita>>> listarCitas() {
        List<Cita> citas = citaService.listarCitas();

        CitaRs<List<Cita>> response = new CitaRs<>();
        response.setStatus(200);
        response.setMensaje("Listado de todas las citas");
        response.setData((Cita) citas);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CitaRs<List<Cita>>> listarPorPaciente(Long id) {
        List<Cita> citas = citaService.listarPorPaciente(id);

        if (citas.isEmpty()) {
            CitaRs<List<Cita>> response = new CitaRs<>();
            response.setStatus(404);
            response.setMensaje("No se encontraron citas para el paciente con ID: " + id);
            response.setData(null);

            return ResponseEntity.status(404).body(response);
        }

        CitaRs<List<Cita>> response = new CitaRs<>();
        response.setStatus(200);
        response.setMensaje("Citas encontradas para el paciente con ID: " + id);
        response.setData((Cita) citas);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CitaRs<List<Cita>>> listarPorMedico(Long id) {
        List<Cita> citas = citaService.listarPorMedico(id);

        if (citas.isEmpty()) {
            CitaRs<List<Cita>> response = new CitaRs<>();
            response.setStatus(404);
            response.setMensaje("No se encontraron citas para el médico con ID: " + id);
            response.setData(null);

            return ResponseEntity.status(404).body(response);
        }

        CitaRs<List<Cita>> response = new CitaRs<>();
        response.setStatus(200);
        response.setMensaje("Citas encontradas para el médico con ID: " + id);
        response.setData((Cita) citas);

        return ResponseEntity.ok(response);

    }
        @Override
        public ResponseEntity<CitaRs<List<Cita>>> listarPorFechaHora() {
            List<Cita> citas = citaService.listarPorFechaHora();

            CitaRs<List<Cita>> response = new CitaRs<>();
            response.setStatus(200);
            response.setMensaje("Listado de todas las citas por fecha desde la mas reciente");
            response.setData((Cita) citas);

            return ResponseEntity.ok(response);

    }
}