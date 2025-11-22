package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.AuditLog;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface AuditService {

    Page<AuditLog> list(
            String username,
            AuditLog.EventType eventType,
            LocalDateTime start,
            LocalDateTime end,
            int page,
            int size
    );
}
