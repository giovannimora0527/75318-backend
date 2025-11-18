package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AuditoriaApi;
import com.uniminuto.clinica.entity.Auditoria;
import com.uniminuto.clinica.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador para la API de auditoría.
 * 
 * @author Sistema
 */
@RestController
public class AuditoriaApiController implements AuditoriaApi {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Override
    public ResponseEntity<List<Auditoria>> listarAuditorias(String tipoEvento) {
        List<Auditoria> auditorias;
        if (tipoEvento != null && !tipoEvento.isEmpty()) {
            auditorias = auditoriaRepository.findByTipoEventoOrderByFechaHoraDesc(tipoEvento);
        } else {
            auditorias = auditoriaRepository.findAllByOrderByFechaHoraDesc();
        }
        return ResponseEntity.ok(auditorias);
    }
}

