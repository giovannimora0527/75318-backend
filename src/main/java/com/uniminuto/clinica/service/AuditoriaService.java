package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Auditoria;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface AuditoriaService {

    void registrarAuditoria(
            String tabla,
            Integer idRegistro,
            String tipoEvento,       // INSERT, UPDATE, DELETE
            Object valoresAntes,
            Object valoresDespues,
            String descripcion
    );

    void registrarAuditoriaExterna(
            String nombreUsuario,
            String tabla,
            Integer idRegistro,
            String tipoEvento,
            Object valoresAntes,
            Object valoresDespues,
            String descripcion);

    Page<Auditoria> listarAuditoria(
            String usuarioFiltro,
            String tipoEventoFiltro,
            String fechaFiltro,
            Pageable pageable
    );
}

