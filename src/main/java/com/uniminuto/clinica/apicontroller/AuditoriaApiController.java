// src/main/java/com/uniminuto/clinica/apicontroller/AuditoriaApiController.java
package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AuditoriaApi;
import com.uniminuto.clinica.entity.AuditoriaSeguridad;
import com.uniminuto.clinica.service.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class AuditoriaApiController implements AuditoriaApi {

    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    public ResponseEntity<List<AuditoriaSeguridad>> obtenerLogs(
            String fechaDesde,
            String fechaHasta,
            String username,
            String motivo) {

        // Convertir strings a LocalDateTime si están presentes
        LocalDateTime fechaDesdeLocal = null;
        LocalDateTime fechaHastaLocal = null;

        if (fechaDesde != null && !fechaDesde.trim().isEmpty()) {
            fechaDesdeLocal = LocalDateTime.parse(fechaDesde, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        if (fechaHasta != null && !fechaHasta.trim().isEmpty()) {
            fechaHastaLocal = LocalDateTime.parse(fechaHasta, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        // Llamar al servicio (ajustaremos el método en el siguiente paso)
        List<AuditoriaSeguridad> logs = auditoriaService.obtenerLogs(
                username,
                motivo,
                fechaDesdeLocal,
                fechaHastaLocal
        );

        return ResponseEntity.ok(logs);
    }
}
