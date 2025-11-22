package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin(origins = "*")
@RequestMapping("/audit")   // ← Igual que usuario = /usuario
public interface AuditApi {

    @GetMapping(
            value = "/listar",
            produces = {"application/json"}
    )
    ResponseEntity<Page<AuditLog>> listar(
            @RequestParam(defaultValue = "") String username,
            @RequestParam(required = false) AuditLog.EventType eventType,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    );
}
