package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AuditoriaApi;
import com.uniminuto.clinica.entity.Auditoria;
import com.uniminuto.clinica.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
        if (tipoEvento != null && !tipoEvento.isEmpty() && !tipoEvento.trim().isEmpty()) {
            auditorias = auditoriaRepository.findByTipoEventoOrderByFechaHoraAsc(tipoEvento);
        } else {
            auditorias = auditoriaRepository.findAllByOrderByFechaHoraAsc();
        }
        return ResponseEntity.ok(auditorias);
    }
    
    @Override
    public ResponseEntity<List<String>> listarTiposEventos() {
        List<String> tiposEventos = auditoriaRepository.findAll()
                .stream()
                .map(Auditoria::getTipoEvento)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        return ResponseEntity.ok(tiposEventos);
    }
}

