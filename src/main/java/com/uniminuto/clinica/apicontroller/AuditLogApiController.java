package com.uniminuto.clinica.controller;

import com.uniminuto.clinica.api.AuditLogApi;
import com.uniminuto.clinica.model.AuditLog;
import com.uniminuto.clinica.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class AuditLogApiController implements AuditLogApi {

    @Autowired
    private AuditLogService auditLogService;

    @Override
    public ResponseEntity<Page<AuditLog>> getAuditLogs(int page, int size, String username, String eventType, LocalDate startDate, LocalDate endDate) {
        Page<AuditLog> logs = auditLogService.getAuditLogs(page, size, username, eventType, startDate, endDate);
        return ResponseEntity.ok(logs);
    }
}
