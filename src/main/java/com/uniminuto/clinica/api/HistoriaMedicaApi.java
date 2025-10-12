package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.HistoriaMedica;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface HistoriaMedicaApi {

    ResponseEntity<?> crearHistoriaMedica(HistoriaMedica HistoriaMedica);

    List<HistoriaMedica> listarHistoriaMedica();

    ResponseEntity<HistoriaMedica> obtenerHistoriaMedicaPorId(Long id);

    @RequestMapping(
            value = "/fechaHora",
            produces = {"application/json"},
            method = RequestMethod.GET
    )
    ResponseEntity<List<HistoriaMedica>> listarHistoriaMedicaPorFechaCreacionDesc();
}
