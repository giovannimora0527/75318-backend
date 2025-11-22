package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.AuditLog;
import com.uniminuto.clinica.entity.AuditLog.EventType;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    Page<AuditLog> findByUsernameInputContaining(String username, Pageable pageable);

    Page<AuditLog> findByEventType(EventType eventType, Pageable pageable);

    Page<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<AuditLog> findByUsernameInputContainingAndTimestampBetween(
            String usernameInput, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<AuditLog> findByEventTypeAndTimestampBetween(
            AuditLog.EventType eventType, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<AuditLog> findByUsernameInputContainingAndEventTypeAndTimestampBetween(
            String usernameInput, AuditLog.EventType eventType,
            LocalDateTime start, LocalDateTime end, Pageable pageable);

}
