package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.AuditoriaSeguridad;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/auditoria")
public interface AuditoriaApi {

    @GetMapping("/logs")
    ResponseEntity<List<AuditoriaSeguridad>> obtenerLogs(
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String motivo
    );
}
