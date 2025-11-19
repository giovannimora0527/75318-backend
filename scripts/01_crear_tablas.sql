-- Script de inicialización de la base de datos
-- Crea todas las tablas necesarias para el sistema de gestión clínica
-- Ejecutar este script en la base de datos 'clinica'

USE clinica;

-- Tabla: especializacion (debe crearse primero porque otras tablas la referencian)
CREATE TABLE IF NOT EXISTS especializacion (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    descripcion TEXT,
    codigo_especializacion VARCHAR(50),
    INDEX idx_codigo (codigo_especializacion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: usuario
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(50),
    fecha_creacion DATETIME,
    activo BOOLEAN DEFAULT TRUE,
    email VARCHAR(255),
    intentos_fallidos INT DEFAULT 0,
    fecha_bloqueo DATETIME NULL,
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: paciente
CREATE TABLE IF NOT EXISTS paciente (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    tipo_documento VARCHAR(10) NOT NULL,
    numero_documento VARCHAR(20) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE,
    genero VARCHAR(1),
    telefono VARCHAR(20),
    direccion TEXT,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    email VARCHAR(255) UNIQUE,
    fecha_registro DATETIME(6),
    INDEX idx_numero_documento (numero_documento),
    INDEX idx_usuario_id (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: medico
CREATE TABLE IF NOT EXISTS medico (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    tipo_documento VARCHAR(10),
    numero_documento VARCHAR(20) NOT NULL UNIQUE,
    nombres VARCHAR(100),
    apellidos VARCHAR(100),
    telefono VARCHAR(20),
    registro_profesional VARCHAR(50) NOT NULL UNIQUE,
    especializacion_id BIGINT UNSIGNED,
    INDEX idx_numero_documento (numero_documento),
    INDEX idx_registro_profesional (registro_profesional),
    INDEX idx_especializacion (especializacion_id),
    FOREIGN KEY (especializacion_id) REFERENCES especializacion(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: medicamento
CREATE TABLE IF NOT EXISTS medicamento (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    presentacion VARCHAR(100),
    fecha_modificacion_registro DATETIME,
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: cita
CREATE TABLE IF NOT EXISTS cita (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT UNSIGNED NOT NULL,
    medico_id BIGINT UNSIGNED NOT NULL,
    fecha_hora DATETIME NOT NULL,
    estado VARCHAR(20) NOT NULL,
    motivo TEXT,
    INDEX idx_paciente (paciente_id),
    INDEX idx_medico (medico_id),
    INDEX idx_fecha_hora (fecha_hora),
    FOREIGN KEY (paciente_id) REFERENCES paciente(id) ON DELETE CASCADE,
    FOREIGN KEY (medico_id) REFERENCES medico(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: receta
CREATE TABLE IF NOT EXISTS receta (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    cita_id BIGINT UNSIGNED NOT NULL,
    medicamento_id BIGINT UNSIGNED NOT NULL,
    dosis TEXT NOT NULL,
    indicaciones TEXT,
    fecha_creacion_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_cita (cita_id),
    INDEX idx_medicamento (medicamento_id),
    FOREIGN KEY (cita_id) REFERENCES cita(id) ON DELETE CASCADE,
    FOREIGN KEY (medicamento_id) REFERENCES medicamento(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: session
CREATE TABLE IF NOT EXISTS session (
    session_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    token VARCHAR(500) NOT NULL,
    fecha_ini_sesion DATETIME,
    fecha_expiracion DATETIME,
    INDEX idx_user_id (user_id),
    INDEX idx_token (token(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: auditoria (ya existe script separado, pero lo incluimos aquí por completitud)
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

