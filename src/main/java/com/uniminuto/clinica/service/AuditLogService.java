package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.AuditLog;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface AuditLogService {
    Page<AuditLog> getAuditLogs(int page, int size, String username, String eventType, LocalDate startDate, LocalDate endDate);
    AuditLog saveLog(AuditLog log);
}
