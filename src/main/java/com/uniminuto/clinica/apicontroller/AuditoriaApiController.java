package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AuditoriaApi;
import com.uniminuto.clinica.entity.Auditoria;
import com.uniminuto.clinica.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.uniminuto.clinica.security.RoleChecker.checkRole;

@RestController
@RequiredArgsConstructor
public class AuditoriaApiController implements AuditoriaApi {

    private final AuditoriaService auditoriaService;

    @Override
    public ResponseEntity<Page<Auditoria>> listarAuditoria(
            String usuarioFiltro,
            String tipoEventoFiltro,
            String fechaFiltro,
            Pageable pageable
    ) {
        checkRole("ADMINISTRADOR");
        Page<Auditoria> auditorias = auditoriaService.listarAuditoria(usuarioFiltro, tipoEventoFiltro, fechaFiltro, pageable);
        return ResponseEntity.ok(auditorias);
    }
}


