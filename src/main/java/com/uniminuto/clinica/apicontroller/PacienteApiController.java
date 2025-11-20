package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.PacienteService;
import com.uniminuto.clinica.utils.SecurityUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class PacienteApiController implements PacienteApi {

    @Autowired
    private PacienteService pacienteService;
    
    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    public ResponseEntity<List<Paciente>> listarPaciente() {
        return ResponseEntity.ok(this.pacienteService.listarLosPacientes());
    }

    @Override
    public ResponseEntity<Paciente>
    encontrarPorDocumento(String numero_documento)
            throws BadRequestException {
        return ResponseEntity.ok(this.pacienteService
                .encontrarPorDocumento(numero_documento));
    }

    @Override
    public ResponseEntity<List<Paciente>> listarPacientePorFechaNacimiento(String orden) {
        return ResponseEntity.ok(this.pacienteService.listarPacientePorFechaNacimiento(orden));
    }

    @Override
    public ResponseEntity<Paciente> guardarPaciente(PacienteRq pacienteRq, HttpServletRequest request) throws BadRequestException {
        try {
            Paciente pacienteGuardado = this.pacienteService.guardarPaciente(pacienteRq);
            
            // Registrar auditoría
            try {
                if (pacienteGuardado != null && pacienteGuardado.getId() != null) {
                    String username = SecurityUtils.getCurrentUsername();
                    HttpServletRequest httpRequest = request;
                    if (httpRequest == null) {
                        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                        httpRequest = attributes.getRequest();
                    }
                    String descripcion = String.format("Se creó el paciente: %s %s (Documento: %s)", 
                            pacienteRq.getNombres(), pacienteRq.getApellidos(), pacienteRq.getNumeroDocumento());
                    auditoriaService.registrarCrear(username != null ? username : "SISTEMA", "PACIENTE", descripcion, httpRequest);
                }
            } catch (Exception e) {
                System.err.println("ERROR al registrar auditoría de creación de paciente: " + e.getMessage());
                e.printStackTrace();
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteGuardado);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al guardar el paciente: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Paciente> actualizarPaciente(Long id, PacienteRq pacienteRq, HttpServletRequest request) throws BadRequestException {
        try {
            Paciente pacienteActualizado = this.pacienteService.actualizarPaciente(id, pacienteRq);
            
            // Registrar auditoría
            try {
                if (pacienteActualizado != null) {
                    String username = SecurityUtils.getCurrentUsername();
                    HttpServletRequest httpRequest = request;
                    if (httpRequest == null) {
                        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                        httpRequest = attributes.getRequest();
                    }
                    String descripcion = String.format("Se actualizó el paciente: %s %s (ID: %d)", 
                            pacienteRq.getNombres(), pacienteRq.getApellidos(), id);
                    auditoriaService.registrarActualizar(username != null ? username : "SISTEMA", "PACIENTE", descripcion, httpRequest);
                }
            } catch (Exception e) {
                System.err.println("ERROR al registrar auditoría de actualización de paciente: " + e.getMessage());
                e.printStackTrace();
            }
            
            return ResponseEntity.ok(pacienteActualizado);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al actualizar el paciente: " + e.getMessage());
        }
    }
}
