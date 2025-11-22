package com.uniminuto.clinica.entity;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @Column(name = "username_input")
    private String usernameInput;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(length = 1000)
    private String description;

    @Column(name = "ip_address")
    private String ipAddress;

    public AuditLog() {}

    public AuditLog(LocalDateTime timestamp, String usernameInput, EventType eventType,
                    String description, String ipAddress) {
        this.timestamp = timestamp;
        this.usernameInput = usernameInput;
        this.eventType = eventType;
        this.description = description;
        this.ipAddress = ipAddress;
    }

    public enum EventType {
        RECOVERY,
        LOGIN_FAIL,
        LOGIN_SUCCESS,
        LOCK,
        UNLOCK
    }

    // Getters / Setters
    public Long getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getUsernameInput() { return usernameInput; }
    public EventType getEventType() { return eventType; }
    public String getDescription() { return description; }
    public String getIpAddress() { return ipAddress; }

    public void setId(Long id) { this.id = id; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setUsernameInput(String usernameInput) { this.usernameInput = usernameInput; }
    public void setEventType(EventType eventType) { this.eventType = eventType; }
    public void setDescription(String description) { this.description = description; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
}
