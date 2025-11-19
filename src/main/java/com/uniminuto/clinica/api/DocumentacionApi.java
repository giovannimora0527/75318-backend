package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.RespuestaRs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * API para obtener documentación del sistema.
 * 
 * @author Sistema Clínica Uniminuto
 * @version 1.0
 */
@CrossOrigin(origins = "*")
@RequestMapping("/documentacion")
public interface DocumentacionApi {
    
    /**
     * Obtiene la documentación completa del sistema.
     * 
     * @param request La petición HTTP actual para construir URLs dinámicas.
     * @return Documentación del sistema en formato JSON
     */
    @RequestMapping(value = "/sistema",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<RespuestaRs> obtenerDocumentacionSistema(HttpServletRequest request);
}

