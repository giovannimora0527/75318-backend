package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.EspecializacionApi;
import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.model.EspecializacionRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.EspecializacionService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.uniminuto.clinica.security.RoleChecker.checkRole;


/**
 *
 * @author lmora
 */
@RestController
public class EspecializacionApiController implements EspecializacionApi {
    
    @Autowired
    private EspecializacionService especializacionService;

    @Override
    public ResponseEntity<List<Especializacion>> listarEspecializaciones() throws BadRequestException {
        checkRole();
        return ResponseEntity.ok(this.especializacionService.listarTodo());
    }

    @Override
    public ResponseEntity<Especializacion> buscarPorCodigo(String codigo) 
            throws BadRequestException {
        checkRole();
        return ResponseEntity.ok(this.especializacionService
                .buscarEspecializacionPorCod(codigo));
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarEspecializacion(@RequestBody @Valid EspecializacionRq especializacionRq) throws BadRequestException {
        checkRole("MEDICO","ADMINISTRADOR");
        return ResponseEntity.ok(this.especializacionService.guardarEspecializacion(especializacionRq));
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarEspecializacion(EspecializacionRq especializacionRq) throws BadRequestException {
        checkRole("MEDICO","ADMINISTRADOR");
        return ResponseEntity.ok(especializacionService.actualizarEspecializacion(especializacionRq));
    }

    @Override
    public ResponseEntity<RespuestaRs> eliminarEspecializacion(Integer idEspecializacion) throws BadRequestException {
        checkRole("MEDICO","ADMINISTRADOR");
        return ResponseEntity.ok(this.especializacionService.eliminarEspecializacion(idEspecializacion));
    }
    
}
