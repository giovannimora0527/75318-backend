package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.AuditoriaLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.uniminuto.clinica.repository.AuditoriaLoginRepository;
import com.uniminuto.clinica.service.AuditoriaLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

@Service
public class AuditoriaLoginServiceImpl implements AuditoriaLoginService {
    @Autowired
    private AuditoriaLoginRepository auditoriaLoginRepository;

    private static final Logger log = LoggerFactory.getLogger(AuditoriaLoginServiceImpl.class);

    /**
     * Logger usado para trazas de la capa de servicio.
     */

    /**
     * Registra un intento de inicio de sesión en la base de datos.
     *
     * @param auditoriaLogin entidad con los datos a persistir (usuario, ip, exito, motivo, fechaHora)
     */
    @Override
    public void registrarIntento(AuditoriaLogin auditoriaLogin) {
        auditoriaLoginRepository.save(auditoriaLogin);
        try {
            log.info("Auditoría guardada: usuario={}, ip={}, exito={}, motivo={}",
                    auditoriaLogin.getUsuario(), auditoriaLogin.getIp(), auditoriaLogin.isExito(), auditoriaLogin.getMotivo());
        } catch (Exception ex) {
            // no bloquear por fallos en logging
        }
    }

    /**
     * Realiza la búsqueda parametrizada delegando en el repositorio.
     *
     * @param usuario filtro por usuario (nullable)
     * @param exito filtro por resultado (nullable)
     * @param start fecha inicio (nullable)
     * @param end fecha fin (nullable)
     * @param pageable paginación solicitada
     * @return página con resultados de auditoría
     */
    @Override
    public Page<AuditoriaLogin> buscar(String usuario, Boolean exito, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        // Delega la búsqueda al repositorio JPA que implementa la consulta parametrizada
        return auditoriaLoginRepository.search(usuario, exito, start, end, pageable);
    }
}
