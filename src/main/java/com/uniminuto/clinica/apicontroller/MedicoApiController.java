package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.MedicoApi;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.model.MedicoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.MedicoService;
import com.uniminuto.clinica.utils.SecurityUtils;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lmora
 */
@RestController
public class MedicoApiController implements MedicoApi {

    @Autowired
    private MedicoService medicoService;
    
    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    public ResponseEntity<List<Medico>> listarMedicos() {
        return ResponseEntity.ok(this.medicoService.listarMedicos());
    }

    @Override
    public ResponseEntity<List<Medico>>
            listarMedicosporEspecialidad(String codigo)
            throws BadRequestException {
        return ResponseEntity.ok(this.medicoService
                .buscarPorEspecialidad(codigo));
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarMedico(MedicoRq medicoRq, HttpServletRequest request) throws BadRequestException {
        RespuestaRs respuesta = this.medicoService.guardarMedico(medicoRq);
        
        // Registrar auditoría solo si la operación fue exitosa
        try {
            if (respuesta != null && respuesta.getStatus() == 200) {
                String username = SecurityUtils.getCurrentUsername();
                HttpServletRequest httpRequest = request;
                if (httpRequest == null) {
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                    httpRequest = attributes.getRequest();
                }
                String descripcion = String.format("Se creó el médico: %s %s (Documento: %s)", 
                        medicoRq.getNombres(), medicoRq.getApellidos(), medicoRq.getDocumento());
                auditoriaService.registrarCrear(username != null ? username : "SISTEMA", "MEDICO", descripcion, httpRequest);
            }
        } catch (Exception e) {
            System.err.println("ERROR al registrar auditoría de creación de médico: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarMedico(MedicoRq medicoRq, HttpServletRequest request) throws BadRequestException {
        RespuestaRs respuesta = this.medicoService.actualizarsMedico(medicoRq);
        
        // Registrar auditoría solo si la operación fue exitosa
        try {
            if (respuesta != null && respuesta.getStatus() == 200) {
                String username = SecurityUtils.getCurrentUsername();
                HttpServletRequest httpRequest = request;
                if (httpRequest == null) {
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                    httpRequest = attributes.getRequest();
                }
                String descripcion = String.format("Se actualizó el médico: %s %s (ID: %s)", 
                        medicoRq.getNombres(), medicoRq.getApellidos(), medicoRq.getId() != null ? medicoRq.getId() : "N/A");
                auditoriaService.registrarActualizar(username != null ? username : "SISTEMA", "MEDICO", descripcion, httpRequest);
            }
        } catch (Exception e) {
            System.err.println("ERROR al registrar auditoría de actualización de médico: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ResponseEntity.ok(respuesta);
    }

}
