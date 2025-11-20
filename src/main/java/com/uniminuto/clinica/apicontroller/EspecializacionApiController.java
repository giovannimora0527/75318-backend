package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.EspecializacionApi;
import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.EspecializacionService;
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
public class EspecializacionApiController implements EspecializacionApi {
    
    @Autowired
    private EspecializacionService servicio;
    
    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    public ResponseEntity<List<Especializacion>> listarEspecializaciones() {
        return ResponseEntity.ok(this.servicio.listarTodo());
    }

    @Override
    public ResponseEntity<Especializacion> buscarPorCodigo(String codigo) 
            throws BadRequestException {
        return ResponseEntity.ok(this.servicio
                .buscarEspecializacionPorCod(codigo));
    }

    @Override
    public ResponseEntity<Especializacion> crearEspecializacion(Especializacion especializacion, HttpServletRequest request)
            throws BadRequestException {
        Especializacion nuevaEspecializacion = this.servicio.crearEspecializacion(especializacion);
        
        // Registrar auditoría - obtener request desde RequestContextHolder si no está inyectado
        try {
            if (nuevaEspecializacion != null && nuevaEspecializacion.getId() != null) {
                String username = SecurityUtils.getCurrentUsername();
                // Obtener request desde RequestContextHolder (Spring puede no inyectarlo desde la interfaz)
                HttpServletRequest httpRequest = request;
                if (httpRequest == null) {
                    try {
                        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                        httpRequest = attributes.getRequest();
                    } catch (Exception e) {
                        System.err.println("No se pudo obtener HttpServletRequest desde RequestContextHolder: " + e.getMessage());
                    }
                }
                String descripcion = String.format("Se creó la especialización: %s (Código: %s)", 
                        nuevaEspecializacion.getNombre(), nuevaEspecializacion.getCodigoEspecializacion());
                auditoriaService.registrarCrear(username != null ? username : "SISTEMA", "ESPECIALIZACION", descripcion, httpRequest);
            }
        } catch (Exception e) {
            System.err.println("ERROR al registrar auditoría de creación de especialización: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ResponseEntity.ok(nuevaEspecializacion);
    }

    @Override
    public ResponseEntity<Especializacion> actualizarEspecializacion(Long id, Especializacion especializacion, HttpServletRequest request)
            throws BadRequestException {
        Especializacion especializacionActualizada = this.servicio.actualizarEspecializacion(id, especializacion);
        
        // Registrar auditoría - obtener request desde RequestContextHolder si no está inyectado
        try {
            if (especializacionActualizada != null) {
                String username = SecurityUtils.getCurrentUsername();
                // Obtener request desde RequestContextHolder (Spring puede no inyectarlo desde la interfaz)
                HttpServletRequest httpRequest = request;
                if (httpRequest == null) {
                    try {
                        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                        httpRequest = attributes.getRequest();
                    } catch (Exception e) {
                        System.err.println("No se pudo obtener HttpServletRequest desde RequestContextHolder: " + e.getMessage());
                    }
                }
                String descripcion = String.format("Se actualizó la especialización: %s (ID: %d, Código: %s)", 
                        especializacionActualizada.getNombre(), id, especializacionActualizada.getCodigoEspecializacion());
                auditoriaService.registrarActualizar(username != null ? username : "SISTEMA", "ESPECIALIZACION", descripcion, httpRequest);
            }
        } catch (Exception e) {
            System.err.println("ERROR al registrar auditoría de actualización de especialización: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ResponseEntity.ok(especializacionActualizada);
    }
    
}
