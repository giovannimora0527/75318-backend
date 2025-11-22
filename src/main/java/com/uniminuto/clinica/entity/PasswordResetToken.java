package com.uniminuto.clinica.entity;

import javax.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación correcta con Usuario
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario user;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiration;

    @Column(nullable = false)
    private Boolean used;

    // 🚀 Constructor vacío (ES OBLIGATORIO)
    public PasswordResetToken() {
    }

    // 🚀 Constructor completo (OPCIONAL, SOLO SI LO NECESITAS)
    public PasswordResetToken(Usuario user, String token, LocalDateTime expiration, Boolean used) {
        this.user = user;
        this.token = token;
        this.expiration = expiration;
        this.used = used;
    }
}

