package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Entidad para registrar logs de auditoría del sistema.
 * 
 * @author Sistema
 */
@Data
@Entity
@Table(name = "auditoria")
public class Auditoria implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;
    
    @Column(name = "nombre_usuario", length = 50)
    private String nombreUsuario;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "tipo_evento", length = 50, nullable = false)
    private String tipoEvento; // "RECUPERACION_CONTRASENA_EXITO", "RECUPERACION_CONTRASENA_ERROR"
    
    @Column(name = "ip_origen", length = 45)
    private String ipOrigen;
}

