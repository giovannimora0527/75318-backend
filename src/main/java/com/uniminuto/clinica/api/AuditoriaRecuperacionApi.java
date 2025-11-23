package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.AuditoriaRecuperacion;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface AuditoriaRecuperacionApi {
    @GetMapping("/auditoria-recuperacion")
    ResponseEntity<Page<AuditoriaRecuperacion>> buscar(
        @RequestParam(required = false) String username,
        @RequestParam(required = false) String descripcion,
        @RequestParam(required = false) String start,
        @RequestParam(required = false) String end,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    );
}
