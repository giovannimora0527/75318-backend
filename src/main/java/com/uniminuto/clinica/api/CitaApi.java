package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Cita;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface CitaApi {

    ResponseEntity<?> crearCita(Cita cita);

    List<Cita> listarCitas();

    ResponseEntity<Cita> obtenerCitaPorId(Long id);

    @RequestMapping(
            value = "/fechaHora",
            produces = {"application/json"},
            method = RequestMethod.GET
    )
    ResponseEntity<List<Cita>> listarCitasPorFechaHoraDesc();
}
