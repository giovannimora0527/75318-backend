package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.RecetaApi;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.RecetaService;
import com.uniminuto.clinica.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class RecetaApiController implements RecetaApi {

    @Autowired
    private RecetaService recetaService;
    
    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    public ResponseEntity<Receta> guardarReceta(RecetaRq recetaRq, HttpServletRequest request) {
        Receta nuevaReceta = recetaService.guardarReceta(recetaRq);
        
        // Registrar auditoría
        try {
            if (nuevaReceta != null && nuevaReceta.getId() != null) {
                String username = SecurityUtils.getCurrentUsername();
                HttpServletRequest httpRequest = request;
                if (httpRequest == null) {
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                    httpRequest = attributes.getRequest();
                }
                String descripcion = String.format("Se creó la receta con ID: %d para la cita ID: %d", 
                        nuevaReceta.getId(), recetaRq.getCitaId());
                auditoriaService.registrarCrear(username != null ? username : "SISTEMA", "RECETA", descripcion, httpRequest);
            }
        } catch (Exception e) {
            System.err.println("ERROR al registrar auditoría de creación de receta: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ResponseEntity.ok(nuevaReceta);
    }

    @Override
    public ResponseEntity<List<Receta>> listarPorReceta(Long citaId) {
        return ResponseEntity.ok(recetaService.listarPorCita(citaId));
    }

    @Override
    public ResponseEntity<List<Receta>> listaRecetas() {
        return ResponseEntity.ok(recetaService.listaRecetas());
    }

    @Override
    public ResponseEntity<Receta> actualizarReceta(Long id, RecetaRq recetaRq, HttpServletRequest request) {
        Receta recetaActualizada = recetaService.actualizarReceta(id, recetaRq);
        
        // Registrar auditoría
        try {
            if (recetaActualizada != null) {
                String username = SecurityUtils.getCurrentUsername();
                HttpServletRequest httpRequest = request;
                if (httpRequest == null) {
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                    httpRequest = attributes.getRequest();
                }
                String descripcion = String.format("Se actualizó la receta con ID: %d", id);
                auditoriaService.registrarActualizar(username != null ? username : "SISTEMA", "RECETA", descripcion, httpRequest);
            }
        } catch (Exception e) {
            System.err.println("ERROR al registrar auditoría de actualización de receta: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ResponseEntity.ok(recetaActualizada);
    }

    @Override
    public ResponseEntity<Void> eliminarReceta(Long id, HttpServletRequest request) {
        recetaService.eliminarReceta(id);
        
        // Registrar auditoría
        try {
            String username = SecurityUtils.getCurrentUsername();
            HttpServletRequest httpRequest = request;
            if (httpRequest == null) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                httpRequest = attributes.getRequest();
            }
            String descripcion = String.format("Se eliminó la receta con ID: %d", id);
            auditoriaService.registrarEliminar(username != null ? username : "SISTEMA", "RECETA", descripcion, httpRequest);
        } catch (Exception e) {
            System.err.println("ERROR al registrar auditoría de eliminación de receta: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Receta> buscarPorId(Long id) {
        Receta receta = recetaService.buscarPorId(id);
        return ResponseEntity.ok(receta);
    }
}
