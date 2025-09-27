package com.uniminuto.clinica.model;

import lombok.Data;


/**
 *
 * @author lmora
 */
<<<<<<< HEAD
/**
 * Mensaje descriptivo del resultado de la operación.
 * Proporciona información legible para el usuario sobre el éxito
 * o falla de la operación solicitada.
 *
 * Ejemplos:
 * - "La cita se ha guardado correctamente."
 * - "La receta se ha guardado correctamente."
 * - "Error: El campo IdPaciente es obligatorio."
 *
 /* @param mensaje String con descripción del resultado de la operación
 */
@Data
public class RespuestaRs {    
    private String mensaje;   //@param mensaje String con descripción del resultado de la operación
    private Integer status; //@param status Código de estado HTTP representando el resultado de la operación
=======
@Data
public class RespuestaRs {    
    private String mensaje;   
    private Integer status;
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
}
