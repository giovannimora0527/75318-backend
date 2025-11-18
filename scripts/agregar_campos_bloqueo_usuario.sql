-- Script para agregar campos de control de intentos fallidos y bloqueo a la tabla usuario
-- Ejecutar este script en la base de datos 'clinica'

USE clinica;

-- Agregar campo para contar intentos fallidos consecutivos
ALTER TABLE usuario 
ADD COLUMN intentos_fallidos INT DEFAULT 0 COMMENT 'NÃºmero de intentos fallidos consecutivos de inicio de sesiÃ³n';

-- Agregar campo para almacenar la fecha/hora del bloqueo temporal
ALTER TABLE usuario 
ADD COLUMN fecha_bloqueo DATETIME NULL COMMENT 'Fecha y hora en que se bloqueÃ³ el usuario temporalmente';

-- Actualizar usuarios existentes para inicializar el campo intentos_fallidos
UPDATE usuario SET intentos_fallidos = 0 WHERE intentos_fallidos IS NULL;

-- Verificar que los campos se agregaron correctamente
DESCRIBE usuario;
