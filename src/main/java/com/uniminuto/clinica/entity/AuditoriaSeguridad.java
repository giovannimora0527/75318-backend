package com.uniminuto.clinica.entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entidad que representa el registro de auditoría de seguridad del sistema.
 * Registra eventos relacionados con autenticación, recuperación de contraseña y otros eventos de seguridad.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auditoria_seguridad")
public class AuditoriaSeguridad implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del registro de auditoría.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Fecha y hora del evento.
     */
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    /**
     * Nombre de usuario ingresado en el formulario.
     * Puede existir o no en el sistema.
     */
    @Column(name = "username_ingresado", nullable = false, length = 50)
    private String usernameIngresado;

    /**
     * Motivo del registro de auditoría.
     * Ejemplos: RECUPERAR_CONTRASENA, LOGIN_FALLIDO, LOGIN_EXITOSO
     */
    @Column(name = "motivo", nullable = false, length = 100)
    private String motivo;

    /**
     * Descripción del error ocurrido.
     * NULL si la operación fue exitosa.
     */
    @Column(name = "descripcion_error", columnDefinition = "TEXT")
    private String descripcionError;

    /**
     * Dirección IP del cliente que realizó la petición.
     */
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    /**
     * User-Agent del navegador/cliente.
     */
    @Column(name = "user_agent", length = 255)
    private String userAgent;

    /**
     * Indica si la operación fue exitosa.
     * true = exitoso, false = fallido
     */
    @Column(name = "exitoso", nullable = false)
    private Boolean exitoso;

    /**
     * Método que se ejecuta antes de persistir la entidad.
     * Establece la fecha y hora automáticamente.
     */
    @PrePersist
    protected void onCreate() {
        if (fechaHora == null) {
            fechaHora = LocalDateTime.now();
        }
    }
}
