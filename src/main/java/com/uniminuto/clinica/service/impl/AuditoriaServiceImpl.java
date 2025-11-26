package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Auditoria;
import com.uniminuto.clinica.repository.AuditoriaRepository;
import com.uniminuto.clinica.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditoriaServiceImpl implements AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    @Override
    public void registrar(String usuario, String accion, String descripcion, String ip) {
        Auditoria auditoria = Auditoria.builder()
                .usuario(usuario)
                .accion(accion)
                .descripcion(descripcion)
                .ip(ip)
                .fechaHora(LocalDateTime.now())
                .build();

        auditoriaRepository.save(auditoria);
    }
}
