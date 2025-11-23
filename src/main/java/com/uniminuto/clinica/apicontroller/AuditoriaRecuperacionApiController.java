package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AuditoriaRecuperacionApi;
import com.uniminuto.clinica.entity.AuditoriaRecuperacion;
import com.uniminuto.clinica.service.AuditoriaRecuperacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@org.springframework.web.bind.annotation.RequestMapping("/auth")
public class AuditoriaRecuperacionApiController implements AuditoriaRecuperacionApi {
    @Autowired
    private AuditoriaRecuperacionService auditoriaRecuperacionService;

    @Override
    public ResponseEntity<Page<AuditoriaRecuperacion>> buscar(String username, String descripcion, String start, String end, int page, int size) {
        LocalDateTime startDt = start != null ? LocalDateTime.parse(start) : null;
        LocalDateTime endDt = end != null ? LocalDateTime.parse(end) : null;
        Page<AuditoriaRecuperacion> resultados = auditoriaRecuperacionService.buscar(username, descripcion, startDt, endDt, PageRequest.of(page, size));
        return ResponseEntity.ok(resultados);
    }
}
