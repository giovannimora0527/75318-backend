package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.model.AuditoriaLogDTO;
import com.uniminuto.clinica.model.AuditoriaLogFiltroDTO;
import com.uniminuto.clinica.model.PageResponse;
import com.uniminuto.clinica.entity.AuditoriaLog;
import com.uniminuto.clinica.repository.AuditoriaLogRepository;
import com.uniminuto.clinica.service.AuditoriaService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuditoriaServiceImpl implements AuditoriaService {
    @Autowired
    private AuditoriaLogRepository repo;
    
    @Override
    public void registrarEvento(String tipoEvento, String descripcion, String username, String ip, String detalles) {
        AuditoriaLog log = new AuditoriaLog();
        log.setTipoEvento(tipoEvento);
        log.setDescripcion(descripcion);
        log.setUsuarioUsername(username);
        log.setDireccionIp(ip);
        log.setDetallesAdicionales(detalles);
        log.setFechaHora(LocalDateTime.now());
        repo.save(log);
    }
    
    @Override
    public void registrarRecuperacionPasswordInvalida(String username, String ip) {
        registrarEvento(
            "RECUPERACION_PASSWORD_FALLIDA",
            "Intento de recuperación de contraseña con username inválido",
            username,
            ip,
            "Usuario ingresado: " + username
        );
    }
    
    @Override
    public void registrarRecuperacionPasswordExitosa(String username, String ip) {
        registrarEvento(
            "RECUPERACION_PASSWORD_EXITOSA",
            "Solicitud de recuperación de contraseña procesada exitosamente",
            username,
            ip,
            "Email enviado al usuario: " + username
        );
    }
    
    @Override
    public void registrarLoginExitoso(String username, String ip) {
        registrarEvento(
            "LOGIN_EXITOSO",
            "Usuario autenticado correctamente",
            username,
            ip,
            null
        );
    }
    
    @Override
    public void registrarLoginFallido(String username, String ip, String motivo) {
        registrarEvento(
            "LOGIN_FALLIDO",
            "Intento de login fallido",
            username,
            ip,
            "Motivo: " + motivo
        );
    }
    
    @Override
    public void registrarBloqueoUsuario(String username, String ip, Integer minutos) {
        registrarEvento(
            "USUARIO_BLOQUEADO",
            "Usuario bloqueado temporalmente por intentos fallidos de login",
            username,
            ip,
            "Tiempo de bloqueo: " + minutos + " minutos"
        );
    }
    
    @Override
    public PageResponse<AuditoriaLogDTO> buscarConFiltros(AuditoriaLogFiltroDTO filtro) {
        int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
        int tamanoPagina = filtro.getTamanoPagina() != null ? filtro.getTamanoPagina() : 10;
        
        Pageable pageable = PageRequest.of(pagina, tamanoPagina);
        
        Page<AuditoriaLog> pageResult = repo.buscarConFiltros(
            filtro.getUsername(),
            filtro.getTipoEvento(),
            filtro.getFechaInicio(),
            filtro.getFechaFin(),
            pageable
        );
        
        List<AuditoriaLogDTO> dtos = pageResult.getContent().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
        
        return new PageResponse<>(
            dtos, 
            pageResult.getNumber(), 
            pageResult.getSize(), 
            pageResult.getTotalElements(), 
            pageResult.getTotalPages(), 
            pageResult.isLast() 
        );
    }
    
    private AuditoriaLogDTO convertirADTO(AuditoriaLog log) {
        return new AuditoriaLogDTO(
            log.getId(),
            log.getFechaHora(),
            log.getUsuarioUsername(), 
            log.getTipoEvento(),
            log.getDescripcion(),
            log.getDireccionIp(), 
            log.getDetallesAdicionales() 
        );
    }
}