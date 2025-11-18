-- Script para crear la tabla de auditoría
-- Base de datos: clinica

CREATE TABLE IF NOT EXISTS auditoria (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    fecha_hora DATETIME NOT NULL,
    nombre_usuario VARCHAR(50),
    descripcion TEXT,
    tipo_evento VARCHAR(50) NOT NULL,
    ip_origen VARCHAR(45),
    INDEX idx_fecha_hora (fecha_hora),
    INDEX idx_tipo_evento (tipo_evento),
    INDEX idx_nombre_usuario (nombre_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

