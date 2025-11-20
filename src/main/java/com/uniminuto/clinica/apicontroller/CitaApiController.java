package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.CitaApi;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.CitaService;
import com.uniminuto.clinica.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author jartunduaga
 */
@RestController
public class CitaApiController implements CitaApi {

    @Autowired
    private CitaService citaService;
    
    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    public ResponseEntity<Cita> guardarCita(CitaRq citaRq, HttpServletRequest request) {
        Cita cita = citaService.guardarCita(citaRq);
        
        // Registrar auditoría
        try {
            if (cita != null && cita.getId() != null) {
                String username = SecurityUtils.getCurrentUsername();
                HttpServletRequest httpRequest = request;
                if (httpRequest == null) {
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                    httpRequest = attributes.getRequest();
                }
                String descripcion = String.format("Se creó la cita con ID: %d para el paciente ID: %d y médico ID: %d", 
                        cita.getId(), citaRq.getPacienteId(), citaRq.getMedicoId());
                auditoriaService.registrarCrear(username != null ? username : "SISTEMA", "CITA", descripcion, httpRequest);
            }
        } catch (Exception e) {
            System.err.println("ERROR al registrar auditoría de creación de cita: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ResponseEntity.ok(cita);
    }

    @Override
    public ResponseEntity<List<Cita>> listarCitaPorFecha(String orden) {
        return ResponseEntity.ok(this.citaService.listarCitaPorFecha(orden));
    }
}
