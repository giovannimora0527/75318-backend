-- Script para insertar datos iniciales en la base de datos
-- Ejecutar después de crear las tablas (01_crear_tablas.sql)

USE clinica;

-- Insertar especializaciones
INSERT INTO especializacion (nombre, descripcion, codigo_especializacion) VALUES
('Medicina General', 'Atención médica general y prevención de enfermedades', 'MG001'),
('Cardiología', 'Especialidad médica que se encarga del corazón y sistema circulatorio', 'CAR001'),
('Pediatría', 'Medicina especializada en la atención de niños y adolescentes', 'PED001'),
('Dermatología', 'Especialidad médica que trata las enfermedades de la piel', 'DER001'),
('Oftalmología', 'Especialidad médica que trata las enfermedades de los ojos', 'OFT001'),
('Traumatología', 'Especialidad médica que trata lesiones del sistema musculoesquelético', 'TRA001')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

-- Insertar usuarios
-- Contraseñas: admin1=123456, medico1=medico123, paciente1=paciente123
-- Las contraseñas están encriptadas con MD5
INSERT INTO usuario (username, password_hash, rol, fecha_creacion, activo, email, intentos_fallidos, fecha_bloqueo) VALUES
('admin1', MD5('123456'), 'ADMIN', NOW(), TRUE, 'admin1@clinica.com', 0, NULL),
('medico1', MD5('medico123'), 'MEDICO', NOW(), TRUE, 'medico1@clinica.com', 0, NULL),
('medico2', MD5('medico123'), 'MEDICO', NOW(), TRUE, 'medico2@clinica.com', 0, NULL),
('paciente1', MD5('paciente123'), 'PACIENTE', NOW(), TRUE, 'paciente1@clinica.com', 0, NULL),
('paciente2', MD5('paciente123'), 'PACIENTE', NOW(), TRUE, 'paciente2@clinica.com', 0, NULL),
('recepcionista1', MD5('recepcion123'), 'RECEPCIONISTA', NOW(), TRUE, 'recepcionista1@clinica.com', 0, NULL)
ON DUPLICATE KEY UPDATE username=VALUES(username);

-- Insertar médicos
INSERT INTO medico (tipo_documento, numero_documento, nombres, apellidos, telefono, registro_profesional, especializacion_id) VALUES
('CC', '12345678', 'Carlos', 'García López', '3001234567', 'REG-MED-001', 1),
('CC', '87654321', 'María', 'Rodríguez Pérez', '3007654321', 'REG-MED-002', 2),
('CC', '11223344', 'Juan', 'Martínez Sánchez', '3001122334', 'REG-MED-003', 3),
('CC', '44332211', 'Ana', 'Fernández Torres', '3004433221', 'REG-MED-004', 4)
ON DUPLICATE KEY UPDATE numero_documento=VALUES(numero_documento);

-- Insertar pacientes (usando INSERT ... SELECT para obtener los IDs de usuario correctos)
INSERT INTO paciente (usuario_id, tipo_documento, numero_documento, nombres, apellidos, fecha_nacimiento, genero, telefono, direccion, activo, email, fecha_registro)
SELECT id, 'CC', '98765432', 'Pedro', 'González Ramírez', '1985-05-15', 'M', '3009876543', 'Calle 123 #45-67', TRUE, 'paciente1@clinica.com', NOW()
FROM usuario WHERE username = 'paciente1' LIMIT 1
ON DUPLICATE KEY UPDATE numero_documento=VALUES(numero_documento);

INSERT INTO paciente (usuario_id, tipo_documento, numero_documento, nombres, apellidos, fecha_nacimiento, genero, telefono, direccion, activo, email, fecha_registro)
SELECT id, 'CC', '55667788', 'Laura', 'Sánchez Morales', '1990-08-20', 'F', '3005566778', 'Avenida 78 #12-34', TRUE, 'paciente2@clinica.com', NOW()
FROM usuario WHERE username = 'paciente2' LIMIT 1
ON DUPLICATE KEY UPDATE numero_documento=VALUES(numero_documento);

INSERT INTO paciente (usuario_id, tipo_documento, numero_documento, nombres, apellidos, fecha_nacimiento, genero, telefono, direccion, activo, email, fecha_registro)
SELECT id, 'CC', '99887766', 'Roberto', 'López Castro', '1988-03-10', 'M', '3009988776', 'Carrera 56 #89-01', TRUE, 'roberto.lopez@email.com', NOW()
FROM usuario WHERE username = 'paciente1' LIMIT 1
ON DUPLICATE KEY UPDATE numero_documento=VALUES(numero_documento);

INSERT INTO paciente (usuario_id, tipo_documento, numero_documento, nombres, apellidos, fecha_nacimiento, genero, telefono, direccion, activo, email, fecha_registro)
SELECT id, 'CC', '33445566', 'Sofía', 'Hernández Díaz', '1992-11-25', 'F', '3003344556', 'Calle 90 #23-45', TRUE, 'sofia.hernandez@email.com', NOW()
FROM usuario WHERE username = 'paciente2' LIMIT 1
ON DUPLICATE KEY UPDATE numero_documento=VALUES(numero_documento);

-- Insertar medicamentos
INSERT INTO medicamento (nombre, descripcion, presentacion, fecha_modificacion_registro) VALUES
('Acetaminofén', 'Analgésico y antipirético', 'Tabletas 500mg', NOW()),
('Ibuprofeno', 'Antiinflamatorio no esteroideo', 'Cápsulas 400mg', NOW()),
('Amoxicilina', 'Antibiótico de amplio espectro', 'Cápsulas 500mg', NOW()),
('Loratadina', 'Antihistamínico para alergias', 'Tabletas 10mg', NOW()),
('Omeprazol', 'Protector gástrico', 'Cápsulas 20mg', NOW()),
('Paracetamol', 'Analgésico y antipirético', 'Jarabe 120mg/5ml', NOW()),
('Diclofenaco', 'Antiinflamatorio y analgésico', 'Tabletas 50mg', NOW()),
('Azitromicina', 'Antibiótico macrólido', 'Cápsulas 500mg', NOW())
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

-- Insertar citas (necesitan pacientes y médicos existentes)
INSERT INTO cita (paciente_id, medico_id, fecha_hora, estado, motivo) VALUES
(1, 1, DATE_ADD(NOW(), INTERVAL 1 DAY), 'PROGRAMADA', 'Consulta general por dolor de cabeza'),
(2, 2, DATE_ADD(NOW(), INTERVAL 2 DAY), 'PROGRAMADA', 'Control cardiológico'),
(1, 3, DATE_ADD(NOW(), INTERVAL 3 DAY), 'PROGRAMADA', 'Consulta pediátrica'),
(2, 4, DATE_ADD(NOW(), INTERVAL 4 DAY), 'PROGRAMADA', 'Revisión dermatológica'),
(3, 1, DATE_ADD(NOW(), INTERVAL -1 DAY), 'COMPLETADA', 'Consulta general - resfriado común'),
(4, 2, DATE_ADD(NOW(), INTERVAL -2 DAY), 'COMPLETADA', 'Control de presión arterial')
ON DUPLICATE KEY UPDATE paciente_id=VALUES(paciente_id);

-- Insertar recetas (necesitan citas y medicamentos existentes)
INSERT INTO receta (cita_id, medicamento_id, dosis, indicaciones, fecha_creacion_registro) VALUES
(5, 1, '1 tableta cada 8 horas', 'Tomar con alimentos. No exceder 4 gramos al día.', NOW()),
(5, 3, '1 cápsula cada 12 horas', 'Completar el tratamiento de 7 días aunque se sienta mejor.', NOW()),
(6, 2, '1 cápsula cada 8 horas', 'Tomar después de las comidas para evitar molestias estomacales.', NOW()),
(6, 5, '1 cápsula en ayunas', 'Tomar 30 minutos antes del desayuno.', NOW())
ON DUPLICATE KEY UPDATE cita_id=VALUES(cita_id);

-- Insertar algunos registros de auditoría de ejemplo
INSERT INTO auditoria (fecha_hora, nombre_usuario, descripcion, tipo_evento, ip_origen) VALUES
(NOW(), 'admin1', 'Sistema inicializado correctamente', 'SISTEMA_INICIALIZADO', '127.0.0.1'),
(DATE_ADD(NOW(), INTERVAL -1 HOUR), 'admin1', 'Usuario admin1 inició sesión exitosamente', 'LOGIN_EXITOSO', '192.168.1.100'),
(DATE_ADD(NOW(), INTERVAL -2 HOUR), 'medico1', 'Usuario medico1 inició sesión exitosamente', 'LOGIN_EXITOSO', '192.168.1.101'),
(DATE_ADD(NOW(), INTERVAL -3 HOUR), 'admin1', 'Se creó un nuevo médico: Dr. Carlos García', 'CREAR_MEDICO', '192.168.1.100'),
(DATE_ADD(NOW(), INTERVAL -4 HOUR), 'admin1', 'Se creó un nuevo paciente: Pedro González', 'CREAR_PACIENTE', '192.168.1.100')
ON DUPLICATE KEY UPDATE fecha_hora=VALUES(fecha_hora);

-- Mensaje de confirmación
SELECT 'Datos iniciales insertados correctamente' AS mensaje;

