package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.MedicamentoApi;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.model.MedicamentoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.MedicamentoService;
import com.uniminuto.clinica.utils.SecurityUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class MedicamentoApiController implements MedicamentoApi {

    @Autowired
    private MedicamentoService medicamentoService;
    
    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    public ResponseEntity<List<Medicamento>> listarMedicamentos() {
        return ResponseEntity.ok(this.medicamentoService.listarMedicamentos());
    }

    @Override
    public ResponseEntity<RespuestaRs> crearMedicamento(MedicamentoRq medicamentoRq, HttpServletRequest request) throws BadRequestException {
        RespuestaRs respuesta = medicamentoService.crearMedicamento(medicamentoRq);
        
        // Registrar auditoría solo si la operación fue exitosa
        try {
            if (respuesta != null && respuesta.getStatus() == 200) {
                String username = SecurityUtils.getCurrentUsername();
                HttpServletRequest httpRequest = request;
                if (httpRequest == null) {
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                    httpRequest = attributes.getRequest();
                }
                String descripcion = String.format("Se creó el medicamento: %s", medicamentoRq.getNombre());
                auditoriaService.registrarCrear(username != null ? username : "SISTEMA", "MEDICAMENTO", descripcion, httpRequest);
            }
        } catch (Exception e) {
            System.err.println("ERROR al registrar auditoría de creación de medicamento: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarMedicamento(Long id, MedicamentoRq medicamentoRq, HttpServletRequest request) throws BadRequestException {
        RespuestaRs respuesta = medicamentoService.actualizarMedicamento(id, medicamentoRq);
        
        // Registrar auditoría solo si la operación fue exitosa
        try {
            if (respuesta != null && respuesta.getStatus() == 200) {
                String username = SecurityUtils.getCurrentUsername();
                HttpServletRequest httpRequest = request;
                if (httpRequest == null) {
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                    httpRequest = attributes.getRequest();
                }
                String descripcion = String.format("Se actualizó el medicamento: %s (ID: %d)", medicamentoRq.getNombre(), id);
                auditoriaService.registrarActualizar(username != null ? username : "SISTEMA", "MEDICAMENTO", descripcion, httpRequest);
            }
        } catch (Exception e) {
            System.err.println("ERROR al registrar auditoría de actualización de medicamento: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ResponseEntity.ok(respuesta);
    }
}