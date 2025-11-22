package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.AuditLog;
import com.uniminuto.clinica.repository.AuditLogRepository;
import com.uniminuto.clinica.service.AuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditLogRepository auditRepo;

    @Override
    public Page<AuditLog> list(
            String username,
            AuditLog.EventType eventType,
            LocalDateTime start,
            LocalDateTime end,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());

        LocalDateTime s = (start == null) ? LocalDateTime.of(1970, 1, 1, 0, 0) : start;
        LocalDateTime e = (end == null) ? LocalDateTime.now().plusMinutes(1) : end;

        boolean hasUser   = username != null && !username.isBlank();
        boolean hasEvent  = eventType != null;

        if (hasUser && hasEvent) {
            return auditRepo.findByUsernameInputContainingAndEventTypeAndTimestampBetween(
                    username, eventType, s, e, pageable);
        }

        if (hasUser) {
            return auditRepo.findByUsernameInputContainingAndTimestampBetween(
                    username, s, e, pageable);
        }

        if (hasEvent) {
            return auditRepo.findByEventTypeAndTimestampBetween(
                    eventType, s, e, pageable);
        }

        return auditRepo.findByTimestampBetween(s, e, pageable);
    }
}
