package com.uniminuto.clinica.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;        // Usuario afectado o involucrado

    @Column(name = "event_type")
    private String eventType;       // LOGIN, RECOVERY_ATTEMPT, UPDATE_USER, ERROR, etc.

    private LocalDateTime timestamp;

    private String description;     // Descripción de la acción realizada

    private String ip;              // (opcional) IP desde donde se realizó la acción

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
}
