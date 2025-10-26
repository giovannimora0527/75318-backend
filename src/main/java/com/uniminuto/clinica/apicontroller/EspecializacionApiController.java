package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.EspecializacionApi;
import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.service.EspecializacionService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;  
import java.util.List;  

/**
 * 
 */
@RestController
public class EspecializacionApiController implements EspecializacionApi {
    
    @Autowired
    private EspecializacionService servicio;

    @Override
    public ResponseEntity<List<Especializacion>> listarEspecializaciones() {
        return ResponseEntity.ok(this.servicio.listarTodo());
    }

    @Override
    public ResponseEntity<Especializacion> buscarPorCodigo(String codigo) 
            throws BadRequestException {
        return ResponseEntity.ok(this.servicio.buscarEspecializacionPorCodigo(codigo));
    }

    @Override
    public ResponseEntity<Especializacion> guardarEspecializacion(@RequestBody Especializacion especializacion) 
            throws BadRequestException {   
        return ResponseEntity.ok(this.servicio.guardarEspecializacion(especializacion));
    }

    @Override
    public ResponseEntity<Especializacion> actualizarEspecializacion(@PathVariable Long id, 
                                                                      @RequestBody Especializacion especializacion) 
            throws BadRequestException {  
        return ResponseEntity.ok(this.servicio.actualizarEspecializacion(id, especializacion));
    }
}
