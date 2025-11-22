package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.MedicoApi;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.model.MedicoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.MedicoService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.uniminuto.clinica.security.RoleChecker.checkRole;

/**
 *
 * @author lmora
 */
@RestController
public class MedicoApiController implements MedicoApi {

    @Autowired
    private MedicoService medicoService;

    @Override
    public ResponseEntity<List<Medico>> listarMedicos() {
        checkRole();
        return ResponseEntity.ok(this.medicoService.listarMedicos());
    }

    @Override
    public ResponseEntity<List<Medico>>
    listarMedicosporEspecialidad(String codigo)
            throws BadRequestException {
        checkRole();
        return ResponseEntity.ok(this.medicoService
                .buscarPorEspecialidad(codigo));
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarMedico(MedicoRq medicoRq) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(this.medicoService.guardarMedico(medicoRq));
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarMedico(MedicoRq medicoRq) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(this.medicoService.guardarMedico(medicoRq));
    }

    @Override
    public ResponseEntity<RespuestaRs> eliminarMedico(Integer idMedico) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(this.medicoService.eliminarMedico(idMedico));
    }
}
