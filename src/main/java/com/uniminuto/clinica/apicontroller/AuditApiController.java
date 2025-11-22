package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AuditApi;
import com.uniminuto.clinica.entity.AuditLog;
import com.uniminuto.clinica.service.AuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class AuditApiController implements AuditApi {

    @Autowired
    private AuditService auditService;

    @Override
    public ResponseEntity<Page<AuditLog>> listar(
            String username,
            AuditLog.EventType eventType,
            LocalDateTime start,
            LocalDateTime end,
            int page,
            int size) {

        return ResponseEntity.ok(
                auditService.list(username, eventType, start, end, page, size)
        );
    }
}
