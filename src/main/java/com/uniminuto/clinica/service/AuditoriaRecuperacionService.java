package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.AuditoriaRecuperacion;

public interface AuditoriaRecuperacionService {
    void registrarIntento(String username, String descripcion);

    org.springframework.data.domain.Page<com.uniminuto.clinica.entity.AuditoriaRecuperacion> buscar(
        String username,
        String descripcion,
        java.time.LocalDateTime start,
        java.time.LocalDateTime end,
        org.springframework.data.domain.Pageable pageable
    );
}
