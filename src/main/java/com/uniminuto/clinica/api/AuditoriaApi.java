package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Auditoria;
import com.uniminuto.clinica.model.AuditoriaRq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/clinica/auditoria")
public interface AuditoriaApi {

    @GetMapping
    ResponseEntity<Page<Auditoria>> listarAuditoria(
            @RequestParam(required = false) String usuarioFiltro,
            @RequestParam(required = false) String tipoEventoFiltro,
            @RequestParam(required = false) String fechaFiltro,
            Pageable pageable
    );
}
