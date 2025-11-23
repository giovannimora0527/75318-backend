package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.AuditoriaLogDTO;
import com.uniminuto.clinica.model.AuditoriaLogFiltroDTO;
import com.uniminuto.clinica.model.PageResponse;
import com.uniminuto.clinica.entity.AuditoriaLog;

public interface AuditoriaService {
    void registrarEvento(String tipoEvento, String descripcion, String username, String ip, String detalles);
    void registrarRecuperacionPasswordInvalida(String username, String ip);
    void registrarRecuperacionPasswordExitosa(String username, String ip);
    void registrarLoginExitoso(String username, String ip);
    void registrarLoginFallido(String username, String ip, String motivo);
    void registrarBloqueoUsuario(String username, String ip, Integer minutos);
    PageResponse<AuditoriaLogDTO> buscarConFiltros(AuditoriaLogFiltroDTO filtro);
}