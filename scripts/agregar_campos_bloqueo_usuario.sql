-- Script para agregar campos de control de intentos fallidos y bloqueo a la tabla usuario
-- Ejecutar este script en la base de datos 'clinica'
-- Este script verifica si la tabla y las columnas existen antes de intentar modificarlas
-- NOTA: Este script es idempotente y seguro para ejecutar múltiples veces

USE clinica;

-- Verificar si la tabla usuario existe
SET @table_exists = (SELECT COUNT(*) FROM information_schema.tables 
                     WHERE table_schema = 'clinica' AND table_name = 'usuario');

-- Verificar si la columna intentos_fallidos existe
SET @col_intentos_exists = (SELECT COUNT(*) FROM information_schema.columns 
                            WHERE table_schema = 'clinica' 
                            AND table_name = 'usuario' 
                            AND column_name = 'intentos_fallidos');

-- Verificar si la columna fecha_bloqueo existe
SET @col_bloqueo_exists = (SELECT COUNT(*) FROM information_schema.columns 
                          WHERE table_schema = 'clinica' 
                          AND table_name = 'usuario' 
                          AND column_name = 'fecha_bloqueo');

-- Agregar campo para contar intentos fallidos consecutivos (solo si la tabla existe y la columna NO existe)
SET @sql1 = IF(@table_exists > 0 AND @col_intentos_exists = 0,
    'ALTER TABLE usuario ADD COLUMN intentos_fallidos INT DEFAULT 0 COMMENT ''Número de intentos fallidos consecutivos de inicio de sesión''',
    'SELECT ''Campo intentos_fallidos ya existe o tabla no existe, saltando'' AS mensaje');
PREPARE stmt1 FROM @sql1;
EXECUTE stmt1;
DEALLOCATE PREPARE stmt1;

-- Agregar campo para almacenar la fecha/hora del bloqueo temporal (solo si la tabla existe y la columna NO existe)
SET @sql2 = IF(@table_exists > 0 AND @col_bloqueo_exists = 0,
    'ALTER TABLE usuario ADD COLUMN fecha_bloqueo DATETIME NULL COMMENT ''Fecha y hora en que se bloqueó el usuario temporalmente''',
    'SELECT ''Campo fecha_bloqueo ya existe o tabla no existe, saltando'' AS mensaje');
PREPARE stmt2 FROM @sql2;
EXECUTE stmt2;
DEALLOCATE PREPARE stmt2;

-- Actualizar usuarios existentes para inicializar el campo intentos_fallidos (solo si la tabla existe)
SET @sql3 = IF(@table_exists > 0 AND @col_intentos_exists > 0,
    'UPDATE usuario SET intentos_fallidos = 0 WHERE intentos_fallidos IS NULL',
    'SELECT ''No se puede actualizar: tabla no existe o columna no existe'' AS mensaje');
PREPARE stmt3 FROM @sql3;
EXECUTE stmt3;
DEALLOCATE PREPARE stmt3;
